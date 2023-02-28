import :math, only: [sqrt: 1]

defmodule Advent do
  def day1(inputFile) do
    lines = formatTextFileIntoLines(inputFile)
    accumulator = buildAccumulator(lines)
    #part1(accumulator)
    sorted = sortAccumulator(accumulator)
    part2(sorted)
  end

  defp formatTextFileIntoLines(inputFile) do
    String.split(File.read!(inputFile), "\r\n")
  end

  defp buildAccumulator(lines) do
    Enum.reduce(lines, [0], fn currentLine, acc = [head|tail] ->
      if(currentLine == "") do
        [0|acc]
      else
        {value, _ } = Integer.parse(currentLine)
        [head + value|tail]
      end
    end)
  end

  defp sortAccumulator(unsortedAccumulator) do Enum.sort(unsortedAccumulator) end

  defp part1(accumulator) do Enum.max(accumulator) end

  defp part2(sortedAccumulator) do
    elements = Enum.take(sortedAccumulator, -3)
    Enum.sum(elements)
  end
end
