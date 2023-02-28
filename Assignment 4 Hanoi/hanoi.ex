defmodule Hanoi do
  def hanoi(0, _, _, _) do [] end
  def hanoi(n, from, aux, to) do
    #Part 1
    #move tower of size n-1 .... ++
    hanoi(n-1, from, to, aux) ++

    #Part 2
    [ {:move, from, to}] ++

    #Part 3
    #move tower of size n-1 ....
    hanoi(n-1, aux, from, to)
  end
end

#Skillnad på termer och mönster. Möstermatchning till _ går bra när någon anropar utifrån, men inte
#när jag internt anropar inne i definitionen.Då kan jag inte skicka in hanoi(1, _, from, to) eller
#det går inte att anropa hanoi(_-1, ...) för vad är _-1?
#När det är noll element att flytta så blir det noll förflyttningar och då returneras tomma listan.
