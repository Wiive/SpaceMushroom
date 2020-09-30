class Bullet extends GameObject
{
  int speed = 260;

  Bullet(float x, float y, int size) 
  {
   super(x, y, size);
   position.x = x;
   position.y = y;
   this.size = size;
   objectColor = color(255);
  }

  void draw()
  {
    position.y = position.y - speed * deltaTime;
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
        if(bullets[i].position.y < 0)
        {
           bullets[i] = null;
        }
      }
    }
  }
}