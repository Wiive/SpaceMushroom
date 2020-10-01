class Bullet extends GameObject
{
  int speed = 260;
  String typeOfBullet;
  

  Bullet(float x, float y, int size, String typeOfBullet) 
  {
   super(x, y, size);
   position.x = x;
   position.y = y;
   this.size = size;
   //We use the strings "enemyBullet" && "playberBullet"
   this.typeOfBullet = typeOfBullet;
   objectColor = color(255);
  }

  void draw()
  {
    if (typeOfBullet == "playerBullet") 
      {
        position.y = position.y - speed * deltaTime;
      }
    else 
    {
      position.y = position.y + speed * deltaTime;
    }
  //Update bullets
    for (int i = 0; i < bullets.length; i++)
    {
     if (bullets[i] == null)
      {
      //No bullet, skip to the next one.
      continue;
      }
      else
      {
        //update bullet...
        fill(objectColor);
        rect(position.x, position.y, size, size*3);

        //Remove bullets out of screen
        if(bullets[i].position.y < 0)
        {
           bullets[i] = null;
        }
        else if(bullets[i].position.y > height)
        {
           bullets[i] = null;
        }
      }
    }
  }
}