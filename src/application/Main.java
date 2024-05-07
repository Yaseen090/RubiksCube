package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

	static AnchorPane root;

	private Box[][] piece = new Box[3][9];
	private Group cube = new Group();

	static double scale = 1.0;
	static double width = 1.0;
	static double height = 1.0;

	private double pieceSize = 20;
	private double pieceGap = 0.65;

	double anchorX, anchorY;
	double anchorAngleX = 0;
	double anchorAngleY = 0;
	final DoubleProperty angleX = new SimpleDoubleProperty(0);
	final DoubleProperty angleY = new SimpleDoubleProperty(0);
	Rotate xRotate;
	Rotate yRotate;

	private PointLight pointLight = new PointLight();

	@Override
	public void start(Stage primaryStage) {
		root = new AnchorPane();
		Scene scene = new Scene(root, 500, 500, Color.LIMEGREEN);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		  AmbientLight ambient_light = new AmbientLight();
		Image image = new Image(getClass().getResourceAsStream("background.jpg"));
		ImageView imageView = new ImageView(image);
		imageView.getTransforms().add(new Translate(0, 0, 0));
		imageView.setFitWidth(500);
		imageView.setFitHeight(500);
		root.getChildren().add(imageView);
		primaryStage.setScene(scene);
		primaryStage.show();
		root.getChildren().addAll(prepareLightSource());
		load(scene);
		scene.setOnKeyPressed(event -> {
			KeyCode keyCode = event.getCode();
			switch (keyCode) {
			case UP:
				cube.setTranslateY(cube.getTranslateY() - 10);
				break;
			case DOWN:
				cube.setTranslateY(cube.getTranslateY() + 10);
				break;
			case LEFT:
				cube.setTranslateX(cube.getTranslateX() - 10);
				break;
			case RIGHT:
				cube.setTranslateX(cube.getTranslateX() + 10);
				break;
			case DIGIT1:
				width *= 1.1; // Increase width
				cube.setScaleX(width);
				break;
			case DIGIT2:
				width *= 0.9; // Decrease width
				cube.setScaleX(width);
				break;
			case DIGIT3:
				height *= 1.1; // Increase height
				cube.setScaleY(height);
				break;
			case DIGIT4:
				height *= 0.9; // Decrease height
				cube.setScaleY(height);
				break;
			default:
				break;
			}
		});

		scene.setOnScroll(event -> {
			double deltaY = event.getDeltaY();
			if (deltaY > 0) {
				scale *= 1.1; // Zoom in
			} else {
				scale *= 0.9; // Zoom out
			}
			cube.setScaleX(scale);
			cube.setScaleY(scale);
			cube.setScaleZ(scale);
		});

		sceneMouseControl(cube, scene);

		primaryStage.show();

cube.getChildren().add(ambient_light);
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void load(Scene scene) {
		createCube();
		root.getChildren().add(getCube());

	}

	private void createCube() {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseMap(new Image(getClass().getResourceAsStream("textureimg.jpeg")));
		material.setSpecularMap(new Image(getClass().getResourceAsStream("relection.png")));
		material.setBumpMap(new Image(getClass().getResourceAsStream("bumpmap.jpg")));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				// todo: initialize the pieces
				piece[i][j] = new Box(pieceSize, pieceSize, pieceSize);

				cube.getChildren().add(piece[i][j]);
			}
		}
		cube.setTranslateX(270);
		cube.setTranslateY(330);
		cube.setTranslateZ(250);
		// todo: Put the pieces in place
		setTopLayer();
		setMiddleLayer();
		setBottomLayer();
	}

	private void setTopLayer() {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseMap(new Image(getClass().getResourceAsStream("redTexture.jpg")));
		material.setSpecularMap(new Image(getClass().getResourceAsStream("redTexture.jpg")));
		material.setBumpMap(new Image(getClass().getResourceAsStream("redTexture.jpg")));
		
		
		for(int i=0;i<9;i++) {
			
			piece[0][i].setMaterial(material);
		}
		// todo: back three
		piece[0][0].setTranslateX(-pieceGap - pieceSize);
		piece[0][0].setTranslateZ(pieceGap + pieceSize);
		piece[0][0].setTranslateY(-pieceGap - pieceSize);

		piece[0][1].setTranslateZ(pieceGap + pieceSize);
		piece[0][1].setTranslateY(-pieceGap - pieceSize);

		piece[0][2].setTranslateX(pieceGap + pieceSize);
		piece[0][2].setTranslateZ(pieceGap + pieceSize);
		piece[0][2].setTranslateY(-pieceGap - pieceSize);

		// todo: middle three
		piece[0][3].setTranslateX(-pieceGap - pieceSize);
		piece[0][3].setTranslateY(-pieceGap - pieceSize);

		piece[0][4].setTranslateY(-pieceGap - pieceSize);

		piece[0][5].setTranslateX(pieceGap + pieceSize);
		piece[0][5].setTranslateY(-pieceGap - pieceSize);

		// todo: front three
		piece[0][6].setTranslateX(-pieceGap - pieceSize);
		piece[0][6].setTranslateZ(-pieceGap - pieceSize);
		piece[0][6].setTranslateY(-pieceGap - pieceSize);

		piece[0][7].setTranslateZ(-pieceGap - pieceSize);
		piece[0][7].setTranslateY(-pieceGap - pieceSize);

		piece[0][8].setTranslateX(pieceGap + pieceSize);
		piece[0][8].setTranslateZ(-pieceGap - pieceSize);
		piece[0][8].setTranslateY(-pieceGap - pieceSize);
	}

	private void setMiddleLayer() {
		
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseMap(new Image(getClass().getResourceAsStream("blueTexture.jpg")));
		material.setSpecularMap(new Image(getClass().getResourceAsStream("blueTexture.jpg")));
		material.setBumpMap(new Image(getClass().getResourceAsStream("blueTexture.jpg")));
		
		
		for(int i=0;i<9;i++) {
			
			piece[1][i].setMaterial(material);
		}

		// todo: back three
		piece[1][0].setTranslateX(-pieceGap - pieceSize);
		piece[1][0].setTranslateZ(pieceGap + pieceSize);

		piece[1][1].setTranslateZ(pieceGap + pieceSize);

		piece[1][2].setTranslateX(pieceGap + pieceSize);
		piece[1][2].setTranslateZ(pieceGap + pieceSize);

		// todo: middle three
		piece[1][3].setTranslateX(-pieceGap - pieceSize);

		piece[1][4].setTranslateY(-pieceGap + pieceSize);

		piece[1][5].setTranslateX(pieceGap + pieceSize);

		// todo: front three
		piece[1][6].setTranslateX(-pieceGap - pieceSize);
		piece[1][6].setTranslateZ(-pieceGap - pieceSize);

		piece[1][7].setTranslateZ(-pieceGap - pieceSize);

		piece[1][8].setTranslateX(pieceGap + pieceSize);
		piece[1][8].setTranslateZ(-pieceGap - pieceSize);
	}

	private void setBottomLayer() {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseMap(new Image(getClass().getResourceAsStream("bulbimage.jpg")));
		material.setSpecularMap(new Image(getClass().getResourceAsStream("bulbimage.jpg")));
		material.setBumpMap(new Image(getClass().getResourceAsStream("bulbimage.jpg")));
		
		
		for(int i=0;i<9;i++) {
			
			piece[2][i].setMaterial(material);
		}
		piece[2][0].setTranslateX(-pieceGap - pieceSize);
		piece[2][0].setTranslateZ(pieceGap + pieceSize);
		piece[2][0].setTranslateY(pieceGap + pieceSize);

		piece[2][1].setTranslateZ(pieceGap + pieceSize);
		piece[2][1].setTranslateY(pieceGap + pieceSize);

		piece[2][2].setTranslateX(pieceGap + pieceSize);
		piece[2][2].setTranslateZ(pieceGap + pieceSize);
		piece[2][2].setTranslateY(pieceGap + pieceSize);

		// todo: middle three
		piece[2][3].setTranslateX(-pieceGap - pieceSize);
		piece[2][3].setTranslateY(pieceGap + pieceSize);

		piece[2][4].setTranslateY(pieceGap + pieceSize);

		piece[2][5].setTranslateX(pieceGap + pieceSize);
		piece[2][5].setTranslateY(pieceGap + pieceSize);

		// todo: front three
		piece[2][6].setTranslateX(-pieceGap - pieceSize);
		piece[2][6].setTranslateZ(-pieceGap - pieceSize);
		piece[2][6].setTranslateY(pieceGap + pieceSize);

		piece[2][7].setTranslateZ(-pieceGap - pieceSize);
		piece[2][7].setTranslateY(pieceGap + pieceSize);

		piece[2][8].setTranslateX(pieceGap + pieceSize);
		piece[2][8].setTranslateZ(-pieceGap - pieceSize);
		piece[2][8].setTranslateY(pieceGap + pieceSize);
	}

	public Group getCube() {
		cube.getChildren().clear();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {

				cube.getChildren().add(piece[i][j]);
			}
		}
		return cube;
	}

	public Box[][] getPiece() {
		return piece;
	}

	private void sceneMouseControl(Group group, Scene scene) {
		// Add XY rotation transformations to the group container
		group.getTransforms().addAll(xRotate = new Rotate(0, Rotate.X_AXIS), yRotate = new Rotate(0, Rotate.Y_AXIS));
		// Bind angleX/angleY with corresponding transformation.
		// When we update angleX/angleY, the transform will also be auto updated.
		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);
		// Listen for mouse press -- Drag start with a click
		scene.setOnMousePressed(event -> {
			// Save start points
			anchorX = event.getSceneX();
			anchorY = event.getSceneY();
			// Save current rotation angle
			anchorAngleX = angleX.get();
			anchorAngleY = angleY.get();
		});
		scene.setOnMouseDragged(event -> {

			angleX.set(anchorAngleX - (anchorY - event.getSceneY())); // Rotate on x-axis
			angleY.set(anchorAngleY + (anchorX - event.getSceneX())); // Rotate on y-axis
		});
	}

	private Node[] prepareLightSource() {
		pointLight.setColor(Color.WHITE);
		pointLight.getTransforms().add(new Translate(250, 100, -50));
		pointLight.setRotationAxis(Rotate.Y_AXIS);

		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.YELLOW);
		material.setSpecularColor(Color.YELLOW);
		material.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("bulbimage.jpg")));

		Sphere sphere = new Sphere(9);
		sphere.getTransforms().add(new Translate(71, 58, -50));
		sphere.setMaterial(material);
		sphere.rotateProperty().bind(pointLight.rotateProperty());
		sphere.rotationAxisProperty().bind(pointLight.rotationAxisProperty());
		return new Node[] { pointLight, sphere };

	}

}
