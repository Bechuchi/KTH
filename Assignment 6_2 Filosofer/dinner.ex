defmodule Dinner do
  def start(nrTimesToEat), do: spawn(fn -> init(nrTimesToEat) end)

  def init(nrTimesToEat) do
    #init 5 processer
    c1 = Chopstick.start()
    c2 = Chopstick.start()
    c3 = Chopstick.start()
    c4 = Chopstick.start()
    c5 = Chopstick.start()

    ctrl = self()

    Philosopher.start(nrTimesToEat, 5, c1, c2, "Arendt", ctrl)
    Philosopher.start(nrTimesToEat, 5, c2, c3, "Hypatia", ctrl)
    Philosopher.start(nrTimesToEat, 5, c3, c4, "Simone", ctrl)
    Philosopher.start(nrTimesToEat, 5, c4, c5, "Elisabeth", ctrl)
    Philosopher.start(nrTimesToEat, 5, c5, c1, "Ayn", ctrl)

    wait(5, [c1, c2, c3, c4, c5])
  end

  def wait(0, chopsticks) do
    Enum.each(chopsticks, fn(c) -> Chopstick.quit(c) end)
  end

  def wait(nrTimesToEat, chopsticks) do
    receive do
      :done ->
        wait(nrTimesToEat - 1, chopsticks)

      #Deadlock
      :abort ->
        IO.format("Dinner aborted")
        Process.exit(self(), :kill)
    end
  end
end
