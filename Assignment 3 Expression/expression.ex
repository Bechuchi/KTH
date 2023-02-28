defmodule Expression do
  @type literal() :: {:nr, number()}
                    | {:variable, atom()}
                    | {:rationalNr, number(), number()}

  @type expression :: literal()
                      | {:addition, expression(), expression()}
                      | {:multiplication, expression(), expression()}
                      | {:subtraction, expression(), expression()}
                      | {:division, expression(), expression()}

  #----------
  # f(x) = x + 0/9
  #----------
  def test1() do
    inputExpression = {:addition,
                        {:variable, :x},
                        {:rationalNr, 0, 9}}

    inputTree = %{x: 7}

    result = evaluate(inputExpression, inputTree)
  end

  #----------
  # f(x) = 2x + 5 + 1/2
  #----------
  def test2() do
    inputExpression = {:addition,
                        {:addition,
                          {:multiplication,
                            {:nr, 2},
                            {:variable, :x}},
                          {:nr, 5}},
                      {:rationalNr, 1, 2}}
    inputTree = %{x: 10}

    result = evaluate(inputExpression, inputTree)
  end

  #----------
  # f(x) = x + 10/0
  #----------
  def test3 do
    inputExpression = {:addition,
                        {:variable, :x},
                        {:rationalNr, 10, 0}}
    inputTree = %{x: 9}

    result = evaluate(inputExpression, inputTree)
  end

  #----------
  # f(x) = x * 11/0
  #----------
  def test4 do
    inputExpression = {:multiplication,
                        {:variable, :x},
                        {:rationalNr, 11, 0}}
    inputTree = %{x: 0}

    result = evaluate(inputExpression, inputTree)
  end

  #----------
  #Evaluations
  #----------
  def evaluate({:nr, n}, _tree) do {:nr, n} end
  def evaluate({:variable, v}, tree) do {:nr, Map.get(tree, v)} end

  def evaluate({:rationalNr, numerator, denominator}, _tree) do
    simplify({:rationalNr, numerator, denominator})
  end

  def evaluate({:addition, firstExpression, secondExpression}, tree) do
    evalutationFirstExpression = evaluate(firstExpression, tree)
    evalutationSecondExpression = evaluate(secondExpression, tree)

    if((evalutationFirstExpression == :undefined) || (evalutationSecondExpression == :undefined)) do
      :undefined
    else
      addition(evalutationFirstExpression, evalutationSecondExpression)
    end
  end

  def evaluate({:multiplication, firstExpression, secondExpression}, tree) do
    evalutationFirstExpression = evaluate(firstExpression, tree)
    evalutationSecondExpression = evaluate(secondExpression, tree)

    if((evalutationFirstExpression == :undefined) || (evalutationSecondExpression == :undefined)) do
      :undefined
    else
      multiplication(evalutationFirstExpression, evalutationSecondExpression)
    end
  end

  #----------
  #Addition
  #----------
  def addition({:rationalNr, n1, d1}, {:rationalNr, n2, d2}) do
    simplify({:rationalNr, (n1*d2)+(n2*d1), d1*d2})
  end

  def addition({:nr, n1}, {:rationalNr, n2, d}) do
    simplify({:rationalNr, (n1*d)+n2, d})
  end

  def addition({:rationalNr, n1, d}, {:nr, n2}) do
    simplify({:rationalNr, n1+(n2*d), d})
  end

  def addition({:nr, n1}, {:nr, n2}) do {:nr, n1+n2} end

  #----------
  #Multilication
  #----------
  def multiplication({:rationalNr, n1, d1}, {:rationalNr, n2, d2}) do
    simplify({:rationalNr, (n1*n2), (d1*d2)})
  end
  def multiplication({:nr, n1}, {:rationalNr, n2, d2}) do
    simplify({:rationalNr, (n1*n2), d2})
  end
  def multiplication({:rationalNr, n1, d1}, {:nr, n2}) do
    simplify({:rationalNr, (n1*n2), d1})
  end
  def multiplication({:nr, n1}, {:nr, n2}) do {:nr, n1*n2} end

  #----------
  #Rational Numbers
  #----------
  def simplify({:rationalNr, numerator, denominator}) do
    cond do
      numerator == 0 ->
        {:nr, 0}
      denominator == 0 ->
        :undefined
      rem(numerator, denominator) == 0 ->
        {:nr, trunc(numerator/denominator)}
      true ->
        commonDenominator = Integer.gcd(numerator, denominator)
        {:rationalNr,
            trunc(numerator/commonDenominator),
            trunc(denominator/commonDenominator)}
    end
  end
end
