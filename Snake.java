package Cartoon;

import java.util.LinkedList;

import javafx.scene.layout.Pane;

/**
 * Models a snake object. Stores all of the body parts and the general direction
 * of the snake.
 *
 */
public class Snake {

	private LinkedList<BodyPart> _bodyParts;
	private Pane _mainPane;
	private Direction _direction;
	private BodyHead _head;

	/**
	 * Constructor for the Snake class. Creates the head of the snake and
	 * initializes a LinkedList to store all of the BodyParts on the snake. Adds the
	 * head to the BodyParts LinkedList.
	 */
	public Snake(Pane mainPane, GameConfig config, Character character) {
		//initialize instance variables
		_bodyParts = new LinkedList<BodyPart>();
		_mainPane = mainPane;
		_direction = Direction.UP;
		
		//create the BodyHead
		this.createHead(character, config);

	}

	/**
	 * Updates the entire Snake. Iterates through each BodyPart and updates it.
	 */
	public void update() {
		// Updates the direction of the BodyHead.
		_head.update(_direction);
		if (_bodyParts.size() > 1) {
			// Sets the current direction of each BodyPart in the list to the last direction
			// of the BodyPart sequentially ahead of it on the list. Updates and redraws
			// each BodyPart using these new coords.
			for (int i = 1; i < _bodyParts.size(); i++) {
				_bodyParts.get(i).update(_bodyParts.get(i - 1).getLastDirection());
			}
		}
	}

	/**
	 * Change the direction of the snake. Called from the ButtonHandler in the game.
	 */
	public void changeDirection(Direction d) {
		if (!(d == _direction.opposite())) {
			_direction = d;
		}
	}

	/**
	 * Accessor method for the BodyParts LinkedList
	 */
	public LinkedList<BodyPart> getBodyParts() {
		return _bodyParts;
	}

	/**
	 * Accessor method for the head of the snake
	 */
	public BodyHead getHead() {
		return _head;
	}

	/**
	 * Creates the head of the snake. If the Character chosen is DWIGHT1, create a
	 * CustomDwightHead which uses my composite shape. Otherwise, create an
	 * ImageHead using the image of the character.
	 */
	public void createHead(Character character, GameConfig config) {
		switch (character) {
		//If the selected character is of type DWIGHT1, create a CustomDwightHead.
		case DWIGHT1: {
			_head = new CustomDwightHead(
					new Coord((int) (config.getNumCol() / 2), (int) (config.getNumRow() / 2), config), _direction,
					_mainPane);
			_bodyParts.add(_head);
			break;
		}
		//Otherwise, create an imagehead
		default:
			_head = new ImageHead(new Coord((int) (config.getNumCol() / 2), (int) (config.getNumRow() / 2), config),
					_direction, _mainPane, character);
			_bodyParts.add(_head);
			break;
		}

	}

	/**
	 * Called when the snake eats the Food object. One new BodyTorso is appended to
	 * the end of the BodyParts list, and the coord of the new BodyTorso is
	 * retrieved using the getStartCoord method.
	 */
	public void grow() {
		Direction d = _bodyParts.getLast().getDirection();
		Coord start = _bodyParts.getLast().getCoord().getStartCoord(d);
		_bodyParts.add(new BodyTorso(start, d, _mainPane));
	}

}
