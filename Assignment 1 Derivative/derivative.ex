defmodule Derivative do
  @type literal() :: {:nr, number()}
                    | {:variable, atom()}

  @type expression :: literal()
                      | {:addition, expression(), expression()}
                      | {:multiplication, expression(), expression()}
                      | {:exponent, expression(), literal()}
                      | {:sinus, expression()}
                      | {:cosinus, expression()}

  #----------------
  #Test1: f(x) = 2x + 4
  #----------------
  def test1() do
    inputExpression = {:addition,
                        {:multiplication,
                          {:nr, 2},
                          {:variable, :x}},
                        {:nr, 4}}
    result = derive(inputExpression, :x)

    IO.write("Expression: #{prettyPrint(inputExpression)}\n")
    IO.write("Derivative: #{prettyPrint(result)}\n")
    IO.write("Simplified: #{prettyPrint(simplify(result))}\n")
    :ok
  end

  #----------------
  #Test2: f(x) = x^3 + 4
  #----------------
  def test2() do
    inputExpression = {:addition,
                        {:exponent,
                          {:variable, :x},
                          {:nr, 3}},
                        {:nr, 4}}
    result = derive(inputExpression, :x)

    IO.write("Expression: #{prettyPrint(inputExpression)}\n")
    IO.write("Derivative: #{prettyPrint(result)}\n")
    IO.write("Simplified: #{prettyPrint(simplify(result))}\n")
    :ok end

  #----------------
  #Test3: f(x) = 2x^2 + 3x + 5
  #----------------
  def test3() do
    inputExpression = {:addition,
                        {:addition,
                           {:multiplication,
                              {:nr, 2},
                              {:exponent, {:variable, :x}, {:nr, 2}}},
                           {:multiplication,
                              {:nr, 3},
                              {:variable, :x}
                           }},
                        {:nr, 5}}
    result = derive(inputExpression, :x)

    IO.write("Expression: #{prettyPrint(inputExpression)}\n")
    IO.write("Derivative: #{prettyPrint(result)}\n")
    IO.write("Simplified: #{prettyPrint(simplify(result))}\n")
    :ok end

  def test4() do
  inputExpression = {:sinus,
                        {:multiplication,
                          {:nr, 2},
                          {:variable, :x}
                        }}

  result = derive(inputExpression, :x)
  IO.write("Expression: #{prettyPrint(inputExpression)}\n")
  IO.write("Derivative: #{prettyPrint(result)}\n")
  IO.write("Simplified: #{prettyPrint(simplify(result))}\n")
  :ok end

  #----------------
  #Rules derivation
  #----------------
  #1. Number
  def derive({:nr, _}, _) do {:nr, 0} end

  #2. Variable
  def derive({:variable, v}, v) do {:nr, 1} end

  #3. Any other variable
  def derive({:variable, _}, _) do {:nr, 0} end

  #4. Addition
  def derive({:addition, firstExpression, secondExpression}, v) do
      {:addition, derive(firstExpression, v), derive(secondExpression, v)} end

  #5. Multiplication
  def derive({:multiplication, firstExpression, secondExpression}, v) do
      {:addition,
        {:multiplication, derive(firstExpression, v), secondExpression},
        {:multiplication, firstExpression, derive(secondExpression, v)}} end

  #6. Exponent
  def derive({:exponent, e, {:nr, n}}, v) do
      {:multiplication,
        {:multiplication,
          {:nr, n},
          {:exponent, e, {:nr, n-1}}},
        derive(e, v)} end

  #7. Sinus
  def derive({:sinus, e}, v) do
      {:multiplication,
            {:cosinus, e},
            derive(e, v)} end


  #----------------
  #Simplifications
  #----------------
  def simplify({:addition, firstExpression, secondExpression}) do
        simplify_addition(simplify(firstExpression), simplify(secondExpression)) end

  def simplify({:multiplication, firstExpression, secondExpression}) do
        simplify_multiplication(simplify(firstExpression), simplify(secondExpression)) end

  def simplify({:exponent, firstExpression, secondExpression}) do
        simplify_exponent(simplify(firstExpression), simplify(secondExpression)) end

  def simplify({:sinus, expression}) do simplify_sinus(expression) end

  def simplify(e) do e end

  #----------------
  #Simplifications: Addition
  #----------------
  #1. Expression added with zero can be simplifed
  def simplify_addition({:nr, 0}, secondExpression) do secondExpression end
  def simplify_addition(firstExpression, {:nr, 0}) do firstExpression end

  #2. Two numbers added can be calculated directly, without calling internal functions
  def simplify_addition({:nr, n1}, {:nr, n2}) do {:nr, n1+n2} end

  #3. Base case: Calling internal functions for addition
  def simplify_addition(firstExpression, secondExpression) do
        {:addition, firstExpression, secondExpression} end

  #----------------
  #Simplifications: Multiplication
  #----------------
  #1. Expression multiplied by zero can be simplifed
  def simplify_multiplication({:nr, 0}, secondExpression) do {:nr, 0} end
  def simplify_multiplication(firstExpression, {:nr, 0}) do {:nr, 0} end

  #2. Expression multiplied by one can be simplifed
  def simplify_multiplication({:nr, 1}, secondExpression) do secondExpression end
  def simplify_multiplication(firstExpression, {:nr, 1}) do firstExpression end

  #3. Two numbers multiplied can be calculated directly, without calling internal functions
  def simplify_multiplication({:nr, n1}, {:nr, n2}) do {:nr, n1*n2} end

  #4. Base case: Calling internal functions for multiplication
  def simplify_multiplication(firstExpression, secondExpression) do
        {:multiplication, firstExpression, secondExpression} end

  #----------------
  #Simplifications: Exponent
  #----------------
  #1. Anything raised by zero can be simplified
  def simplify_exponent(_, {:nr, 0}) do {:nr, 1} end

  #2. Anything raised by one can be simplified
  def simplify_exponent(firstExpression, {:nr, 1}) do firstExpression end

  #3. Base case: Calling internal functions for exponent
  def simplify_exponent(firstExpression, secondExpression) do
        {:exponent, firstExpression, secondExpression} end

  def simplify_sinus(expression) do {expression} end
  #----------------
  #Pretty printing: Takes a mathematical expression and returns a string
  #----------------
  #1. Number
  def prettyPrint({:nr, n}) do "#{n}" end

  #2. Variable
  def prettyPrint({:variable, v}) do "#{v}" end

  #3. Addition of two expressions
  def prettyPrint({:addition, firstExpression, secondExpression}) do
        "(#{prettyPrint(firstExpression)} + #{prettyPrint(secondExpression)})" end

  #4. Muliplication of two expressions
  def prettyPrint({:multiplication, firstExpression, secondExpression}) do
        "#{prettyPrint(firstExpression)}*#{prettyPrint(secondExpression)}" end

  #5. Exponent
  def prettyPrint({:exponent, firstExpression, secondExpression}) do
        "(#{prettyPrint(firstExpression)})^(#{prettyPrint(secondExpression)})" end

def prettyPrint({:sinus, expression}) do
      "sin(#{prettyPrint(expression)})" end

def prettyPrint({:cosinus, expression}) do
      "cos(#{prettyPrint(expression)})" end
end
