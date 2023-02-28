@dream 800
@eat 100
@delay 0

@timeout 1000

defmodule Philosopher do
  def start(hunger, leftChopstick, rightChopstick, name, ctrl) do
    spawn_link(fn -> init() end)
  end

  def init() do
    dreaming(hunger, strength, left, right, name, ctrl, gui)
  end

  ############
  #Dreaming
  ############
  #######################
  #Has eaten enough times
  #######################
  defp dreaming(0, strength, _left, _right, name, ctrl, gui) do
    IO.puts("#{name} is happy, strength is still #{strength}!")
    send(gui, {:action, name, :done})
    send(ctrl, :done)
  end

  #######################
  #Still hungry but strenth is finnished
  #######################
  defp dreaming(hunger, 0, _left, _right, name, ctrl, gui) do
    IO.puts("#{name} dies! Still hungry but no strenth left")
    send(ctrl, :done)
  end

  #######################
  #Hungry and has strength left
  #######################
  defp dreaming(hunger, strength, left, right, name, ctrl, gui) do
    IO.puts("#{name} is dreaming...")
    ##  this is where we sleep
    delay(@dream)

    IO.puts("#{name} wakes up")
    waiting(hunger, strength, left, right, name, ctrl, gui)
  end

  ############
  #Waiting
  ############
  def waiting(hunger, strength, leftChopstick, rightChopstick, name, ctrl) do
    case requestChopstick(leftChopstick) do
      :granted ->
        #Left chopstick was granted, now request right chopstick
        IO.puts("#{name} got the left stick granted...")
        case requestChopstick(rightChopstick) do
          :granted ->
            #Both chopsticks were granted
            eating(hunger, strength, leftChopstick, rightChopstick, name, ctrl)

          :timeout ->
            #Right chopstick request timed out, return the left chopstick
            returnChoptick(leftChopstick)
            IO.format("Philosopher gave back left stick due to timeout on the right")
        end
      :timeout ->
        #Left chopstick request timed out
        {:error, "Philosopher cannot eat with only one chopstick."}
    end
  end

  ############
  #Eating
  ############
 defp eating(hunger, strength, leftChopstick, rightChopstick, name, ctrl) do
  IO.puts("#{name} is eating...")

  delay(@eat)

  #Return the chopsticks
  returnChoptick(leftChopstick)
  returnChoptick(rightChopstick)
  {:ok, "Philosopher ate and returned chopsticks."}

  #Correct the parameters
  dreaming(hunger-1, strength, leftChopstick, rightChopstick, name, ctrl)
end

  ############
  #Chopsticks
  ############
  def requestChopstick(chopstick) do
    answer = chopstick.request(Process.whereis(chopstick))
    answer
  end

  def recieveChoptick(chopstick) do
    IO.puts("received a chopstick!")
  end

  def returnChoptick(chopstick) do IO.puts("returned a chopstick!") end
end
