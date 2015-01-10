"""  
  Author: Faiz Khan

  Palindrome number tackles the traditional palindrome detection problem but 
  changes the input to integers and does not allow extra space. 

  Source: http://leetcode.com/2012/01/palindrome-number.html
"""

import math as m
def magnitude(x):
  """Returns the order of magnitude of x in base 10"""
  return int(m.log(x,10))

def left_digit(x): 
  """Gets the left most digit of the provided number""" 
  return int(x/10**magnitude(x))

def right_digit(x): 
  """Returns the right most digit of x"""
  return int(x%10)

def is_single_digit(x): 
  """Returns True if its a single digit number, False otherwise"""
  return right_digit(x) == 0

def is_palindrome(x): 
  """Returns true if the given number is a Palindrome 

  Arguments: 
  x -- integer for palindrome check. Negative signs are ignored. Single digits return true.  

  Exceptions: 
  Throws a type error if the argument is not an integer 
  """

  # Non-integer types are not handled 
  if not isinstance(x, int): raise TypeError

  # negative signs ignored
  x = abs(x) 

  while not is_single_digit(x): 
    # Compare the first and last digit
    if(right_digit(x) != left_digit(x)):
      return False 

    x = x - left_digit(x)*(10**magnitude(x))
    x = x / 10
  return True

if __name__ == "__main__":
  print("1:",is_palindrome(1))
  print("0:",is_palindrome(0))
  print("121:", is_palindrome(121))
  print("-121:", is_palindrome(-121))
  print("1221:", is_palindrome(1221))
  print("555333555:",is_palindrome(555333555))
  print("5553663555:", is_palindrome(5553663555))
  print("5553363555:", is_palindrome(5553363555))
  print("18651:", is_palindrome(18651))
  print("-18651:", is_palindrome(-18651))

  try: 
    is_palindrome(5.0)
    is_palindrome("Something")
  except TypeError: 
    print("Type Error caught") 
