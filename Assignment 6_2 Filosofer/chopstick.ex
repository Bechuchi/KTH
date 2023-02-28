defmodule Chopstick do
  def start() do
    chopstick = spawn_link(fn -> available() end)
  end

  ######################
  #STATES
  ######################
  #STATE 1
  ########
  def available() do
    #Lyssna på sin msg que
    receive do
      #Should return the granted message and move to the state gone
      #Updates the process that sended the request and lets it know now it is granted
      #Then this chopstick process itself should update its state to gone
      {:request, from} ->
        send(from, :granted)
        gone()

      :quit -> :ok
    end
  end

  ########
  #STATE 2
  ########
  def gone() do
    receive do
      {:return, x} ->
        available()

      :quit -> :ok
    end
  end

  ######################
  #OPERATIONS
  ######################
  ############
  #OPERATION 1
  ############
  def request({:chopstick, pid}) do
    receiver = pid
    message = {:request, self()}

    send(receiver, message)

    receive do
      :granted -> :ok
    end
  end

  ############
  #OPERATION 2
  ############
  def return({:chopstick, pid}) do
    receiver = pid
    message = {:return, self()}

    send(receiver, message)

    receive do
      ... -> :ok
    end
  end

  ############
  #Make a process sleep for a random time
  ############
  def sleep(0) do :ok end
  def sleep(t) do
    :timer.sleep(:rand.uniform(t))
  end
end
