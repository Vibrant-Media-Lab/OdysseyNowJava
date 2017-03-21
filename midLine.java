import java.awt.*;
import java.awt.event.*;

public class midLine{
    
	private ODSYRunner game;

	public midLine(ODSYRunner game) {
		this.game = game;
	}

	public void paint(Graphics2D g) {
		g.fillRect(game.xSize/2, 0, 30, game.ySize);
	}
    
}