class Player extends GameObject
{
	float speed = 3;
	float maxSpeed = 10;
	PVector acceleration;
	float startPos;


	Player(float x, float y, int size)
	{
		super(x,y,size);
		push();
		startPos = y;
		this.size = size;
		position.x = x;
		velocity = new PVector();
		pop();
	}

	void update()
	{
		acceleration = input();
		velocity.add(acceleration);
		velocity.limit(maxSpeed);

		PVector move = velocity.copy();

		move.mult(speed);

		position.add(move);
	}

	void draw()
	{
		fill(255);
		ellipse(position.x, startPos, size, size);
	}
}