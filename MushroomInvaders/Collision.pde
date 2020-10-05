 boolean bulletCollision(GameObject gameObject, Bullet bullet)
{ 
	int maxDistance = gameObject.size/2 + bullet.size;

	if(abs(gameObject.position.x - bullet.position.x) > maxDistance || abs(gameObject.position.y - bullet.position.y) > maxDistance)
	{
		return false;
	}

	else if(dist(gameObject.position.x, gameObject.position.y, bullet.position.x, bullet.position.y) > maxDistance)
	{
		return false;
	}

	else
	{
		return true;
	}
}