boolean moveLeft;
boolean moveRight;
PVector inputVector;

void keyPressed()
{
    if (keyCode == LEFT || key == 'a')
      moveLeft = true;
    else if (keyCode == RIGHT || key == 'd')
      moveRight = true;

     //Spawn new bullet it we press "space-bar"
    if (keyPressed && key == 32) 
    {  
      //Find empty spot in array, create list.
      for (int i = 0; i < bullets.length; i++)
      {
        if (bullets[i] == null)
        {
          bullets[i] = new Bullet(player.position.x, player.position.y, 10);   
          //we are done, break/quit the loop.
          break;
        }
      }
    } 
}

void keyReleased()
{
    if (keyCode == LEFT || key == 'a')
      moveLeft = false;
    else if (keyCode == RIGHT || key == 'd')
      moveRight = false;
}

PVector input()
{
  inputVector = new PVector();
  
  inputVector.x = 0;
  
  if (moveLeft) {
    inputVector.x -= 1;
  }
  if (moveRight) {
    inputVector.x += 1;
  }

  return inputVector;
}
