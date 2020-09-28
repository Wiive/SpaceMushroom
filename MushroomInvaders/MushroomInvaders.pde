Player player;



void setup()
{
	background(0);
	size(800,1000);
	Player player = new Player(400,850,30);
}

void draw()
{
	player.update();
	player.draw();
}
