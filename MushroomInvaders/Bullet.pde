class Bullet extends GameObject

{
Bullet[] bullets; 

 Bullet(float x, float y) 
{
  bullets = new Bullet[10];
}

void draw() {
  //Update bullets
  for (int i = 0; bullets.length; i++) {
    if (bullets[i] == null) {
      //No bullet, skip to the next one.
      continue;
    }
    else
    {
      //found a bullet, update it.
    }
  }

  //Spawn new bullet it we press "space-bar"
  if (keyPressed && key == 32) {  
      //Find empty spot in array, create list.
      for (int i = 0; bullets.length; i++) {
        if (bullets[i] == null) {
          bullets[i] = new Bullet(player.position);
          //we are done, break/quit the loop.
          break;
        }
      }
  }
}
}