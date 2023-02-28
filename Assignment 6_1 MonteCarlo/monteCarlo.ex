defmodule Program do
  #-------------
  #Dart
  #-------------
  def dart(radius) do
    x = Enum.random(0..radius)
    y = Enum.random(0..radius)

    dartPosition = addXSquareAndYSquare(x, y)
    board = :math.pow(radius, 2)

    isDartWithinTheBoard(board, dartPosition)
  end

  defp addXSquareAndYSquare(x, y) do
    xSquare = :math.pow(x, 2)
    ySquare = :math.pow(y, 2)

    sum = xSquare + ySquare
    sum
  end

  defp isDartWithinTheBoard(board, dartPosition) do compare(board, dartPosition) end

  defp compare(board, dartPosition) do board > dartPosition end

  #-------------
  #A group of rounds to estimate
  #-------------
  def round(0, _, accumulator) do accumulator end
  def round(nrOfDarts, boardRadious, accumulator) do
    if dart(boardRadious) do
      round(nrOfDarts-1, boardRadious, accumulator + 1)
    else
      round(nrOfDarts-1, boardRadious, accumulator)
    end
  end

  #-------------
  #Test
  #-------------
  def rounds(nrOfRounds, nrOfDarts, boardRadious) do
    rounds(nrOfRounds, nrOfDarts, 0, boardRadious, 0)
  end

  def rounds(0, _, totalNrDartsThrown, _, accumulator) do :io.format("\nInbuild: ~.8f", [:math.pi()]) end

  def rounds(nrOfRounds, nrOfDarts, totalNrDartsThrown, boardRadious, accumulator) do
    accumulator = round(nrOfDarts, boardRadious, accumulator)
    totalNrDartsThrown = totalNrDartsThrown + nrOfDarts

    pi = 4 * (accumulator/totalNrDartsThrown)

    :io.format("Estimated value for PI: ~.8f ~.8f\n", [pi, (pi - :math.pi())])

    rounds(nrOfRounds-1, nrOfDarts, totalNrDartsThrown, boardRadious, accumulator)
  end

end
