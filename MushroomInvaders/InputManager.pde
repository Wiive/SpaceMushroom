boolean moveLeft;
boolean moveRight;
PVector inputVector;

//Key pressed, set our movement variables to true
void keyPressed()
{
    if (keyCode == LEFT || key == 'a')
      moveLeft = true;
    else if (keyCode == RIGHT || key == 'd')
      moveRight = true;
}

//When a key is released, we will set our variable to false
void keyReleased()
{
    if (keyCode == LEFT || key == 'a')
      moveLeft = false;
    else if (keyCode == RIGHT || key == 'd')
      moveRight = false;
}

//Returns a PVector input
PVector input()
{
  //Reset our input to zero
  inputVector.x = 0;

  //Change our input based on user input
  if (moveLeft) {
    inputVector.x -= 1;
  }
  if (moveRight) {
    inputVector.x += 1;
  }

  //return our result
  return inputVector;
}
