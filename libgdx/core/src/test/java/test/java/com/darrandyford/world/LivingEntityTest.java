package test.java.com.darrandyford.world;

import main.java.com.darrandyford.entities.living.LivingEntity;
import main.java.com.darrandyford.entities.living.characters.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class LivingEntityTest {

	@Test
	void directionUpMinShouldSetSpriteUp() {
		LivingEntity entity = Mockito.mock(LivingEntity.class);
		entity.setDirection();
	}

	@Test
	void directionUpMaxShouldSetSpriteUp() {
	}

	@Test
	void directionRightMinShouldSetSpriteRight() {
	}

	@Test
	void directionRightMaxShouldSetSpriteRight() {
	}

	@Test
	void directionDownMinShouldSetSpriteDown() {
	}

	@Test
	void directionDownMaxShouldSetSpriteDown() {
	}

	@Test
	void directionLeftMinShouldSetSpriteLeft() {
	}

	@Test
	void directionLeftMaxShouldSetSpriteLeft() {
	}


	@Test
	void setSideSprite() {
	}

	@Test
	void setFrontSprite() {
	}

	@Test
	void setBackSprite() {
	}
}
