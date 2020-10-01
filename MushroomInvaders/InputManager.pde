boolean moveLeft;
boolean moveRight;
PVector inputVector;
boolean resetButtonDown = false;
float shootCooldown = 0.33;

void keyPressed()
{
    if (keyCode == LEFT || key == 'a')
      moveLeft = true;
    else if (keyCode == RIGHT || key == 'd')
      moveRight = true;

    if(shootCooldown <= 0){
      if (keyPressed && key == 32) 
      {  
        for (int i = 0; i < bullets.length; i++)
        {
          if (bullets[i] == null)
          {
            bullets[i] = new Bullet(player.position.x, player.position.y, 5, "playerBullet");
            shootCooldown = 0.33;
            break;
          }
        }
      } 
    }
   
    if(gameOver)
    {
      if (resetButtonDown == false && key == 'r')
      {
      resetButtonDown = true;
      restartGame();
      }
    }
}

void keyReleased()
{
    if (keyCode == LEFT || key == 'a')
      moveLeft = false;
    else if (keyCode == RIGHT || key == 'd')
      moveRight = false;

    if (key == 'r') 
    {
    resetButtonDown = false;
    gameOver = false;
    }
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
