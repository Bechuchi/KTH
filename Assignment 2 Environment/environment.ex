defmodule EnvList do
  def new() do [] end
  #----------------
  #Test:
  #----------------
  def testAdd() do
    list = [{:a, 12}, {:b, 13}]
    result1 = add([], :c, 14)
    result2 = add(list, :c, 9)
    result3 = add(list, :a, 9)
  end

  def testLookup() do
    list = [{:a, 12}, {:b, 13}, {:c, 14}]
    #lookup(list, :b)
    lookup(list, :d)
  end

  def testRemove() do
    list = [{:a, 12}, {:b, 13}, {:c, 14}, {:d, 15}]
    remove(list, :c)
  end

  def add([], key, value) do  [{key,value}] end
  def add([{key,_}|tail], key, value) do [{key, value}|tail] end
  def add([head | tail ], key, value) do [ head |  add(tail, key, value) ] end

  def lookup([head|[]], key) do nil end
  def lookup([{key, value}|_], key) do {key, value} end
  def lookup([head | tail], key) do lookup(tail, key) end

  def remove([], key) do [] end
  def remove([{key, _}|tail], key) do tail end
  def remove([head|tail], key) do [head| remove(tail, key)] end
end

defmodule EnvTree do
  def new() do nil end

  def add(nil, key, value) do {:node, key, value, nil, nil} end
  def add({:node, key, _, leftTree, rightTree}, key, value) do {:node, key, value, leftTree, rightTree} end

  def add({:node, k, v, leftTree, rightTree}, key, value) do
      if key < k do
        {:node, k, v, add(leftTree, key, value), rightTree}
      else
        {:node, k, v, leftTree, add(rightTree, key, value)}
      end
  end

  def lookup(nil, _key) do nil end
  def lookup({:node, key, value, _left, _right}, key) do {key, value} end
  def lookup({:node, k, _, left, _right}, key) when key < k do lookup(left, key) end
  def lookup({:node, _, _, _left, right}, key) do lookup(right, key) end

  def remove(nil, _) do nil end
  def remove({:node, key, _, nil, rightTree}, key) do rightTree end
  def remove({:node, key, _, leftTree, nil}, key) do leftTree end
  def remove({:node, key, _, leftTree, rightTree}, key) do
    {key, value, rest} = leftmost(rightTree)
    {:node, key, value, leftTree, rest}
  end
  def remove({:node, k, v, leftTree, rightTree}, key) when key < k do
    {:node, k, v, remove(leftTree, key), rightTree} end
  def remove({:node, k, v, leftTree, rightTree}, key) do
    {:node, k, v, leftTree, remove(rightTree, key)} end

  def leftmost({:node, key, value, nil, rest}) do {key, value, rest} end
  def leftmost({:node, k, v, left, right}) do
    {key, value, rest} = leftmost(left)
    {key, value, {:node, k, v, rest, right}}
  end
end

defmodule Bench do
  def bench() do bench(1000) end

  def bench(n) do

    ls = [16,32,64,128,256,512,1024,2*1024,4*1024,8*1024]
    :io.format("# benchmark of key-value store as a list, a tree and Map: ~w operations, time per operation in us\n", [n])
    :io.format("~6.s~8.s~-36.s~-36.s~-36.s\n", ["n", "", "add", "lookup", "remove"])
    :io.format("~18.s~12.s~12.s~12.s~12.s~12.s~12.s~12.s~12.s\n", ["list", "tree", "map", "list", "tree", "map", "list", "tree", "map"])
    Enum.each(ls, fn (i) ->
      {i, tla, tta, tma, tll, ttl, tml, tlr, ttr, tmr} = bench(i, n)
      :io.format("~6.w~12.2f~12.2f~12.2f~12.2f~12.2f~12.2f~12.2f~12.2f~12.2f\n", [i,tla/n, tta/n, tma/n, tll/n, ttl/n, tml/n, tlr/n, ttr/n, tmr/n])
    end)

    :ok
  end

  def bench(i, n) do
    seq = Enum.map(1..i, fn(_) -> :rand.uniform(i) end)

    list = Enum.reduce(seq,  EnvList.new(),  fn(e, list) -> EnvList.add(list, e, :foo) end)
    tree = Enum.reduce(seq,  EnvTree.new(),  fn(e, tree) -> EnvTree.add(tree, e, :foo) end)
    map = Enum.reduce(seq,  Map.new(),  fn(e, map) -> Map.put(map, e, :foo)  end)

    seq = Enum.map(1..n, fn(_) -> :rand.uniform(i) end)

    {tla, _} = :timer.tc(fn() -> Enum.each(seq, fn(e) -> EnvList.add(list, e, :foo) end) end)
    {tta, _} = :timer.tc(fn() -> Enum.each(seq, fn(e) -> EnvTree.add(tree, e, :foo) end) end)
    {tma, _} = :timer.tc(fn() -> Enum.each(seq, fn(e) -> Map.put(map, e, :foo) end) end)

    {tll, _} = :timer.tc(fn() -> Enum.each(seq, fn(e) -> EnvList.lookup(list, e) end) end)
    {ttl, _} = :timer.tc(fn() -> Enum.each(seq, fn(e) -> EnvTree.lookup(tree, e) end) end)
    {tml, _} = :timer.tc(fn() -> Enum.each(seq, fn(e) -> Map.get(map, e) end) end)

    {tlr, _} = :timer.tc(fn() -> Enum.each(seq, fn(e) -> EnvList.remove(list, e) end) end)
    {ttr, _} = :timer.tc(fn() -> Enum.each(seq, fn(e) -> EnvTree.remove(tree, e) end) end)
    {tmr, _} = :timer.tc(fn() -> Enum.each(seq, fn(e) -> Map.delete(map, e) end) end)

    {i, tla, tta, tma, tll, ttl, tml, tlr, ttr, tmr}
  end
end
