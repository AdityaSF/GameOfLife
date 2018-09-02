package adiitya.life.input;

import com.badlogic.gdx.Gdx;

public class KeyStroke {

	public final int id;
	public boolean isPressed = false;

	public KeyStroke(int id) {
		this.id = id;
	}

	public void release() {
		isPressed = false;
	}

	public void press() {
		isPressed = true;
	}

	public boolean isJustPressed() {
		return Gdx.input.isKeyJustPressed(id);
	}
}
