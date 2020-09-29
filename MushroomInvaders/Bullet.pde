class Bullet extends GameObject

{
 Bullet(float x, float y, int size) 
{

 super(x, y, size);
 position.x = x;
 position.y = y;
 this.size = size;

}

void draw() {
  //Update bullets
  for (int i = 0; i < bullets.length; i++) {
    if (bullets[i] == null) {
      //No bullet, skip to the next one.
      continue;
    }
    else
    {
      //found a bullet, update it.
    }
  }

 }
}
