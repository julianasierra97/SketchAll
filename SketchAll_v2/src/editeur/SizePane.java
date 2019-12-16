package editeur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class SizePane extends JPanel {
	private static final long serialVersionUID = 1L;

	EditeurClient editor;

	GridBagConstraints gbc;
	List<SizeButton> buttonList = new ArrayList<SizeButton>();
	String newShape = "Ellipse";
	int newSize = 7 ;

	ButtonGroup sizeGroup = new ButtonGroup();
	SizeButton rectButton = new SizeButton("Rectangle", 0);
	SizeButton ellipseButton = new SizeButton("Ellipse", 0);
	SizeButton deleteButton = new SizeButton("Delete", 0);
	SizeButton smallButton = new SizeButton("Drawing", 6);
	SizeButton mediumButton = new SizeButton("Drawing", 9);
	SizeButton bigButton = new SizeButton("Drawing", 12);

	public SizePane(EditeurClient editor, ToolPane tool) {
		this.editor = editor;
		setLayout(new GridBagLayout());
		setBackground(new Color(240,240,240));

		TitledBorder border;
		Border borderLine;
		Color borderColor = new Color(160,160,160);
		Font borderFont = new Font("Arial",1,17);
		borderLine = BorderFactory.createLineBorder(borderColor);
		border = BorderFactory.createTitledBorder(borderLine, "Drawings");
		border.setTitleFont(borderFont);
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitleColor(borderColor);
		setBorder(border);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;	//Comment remplir l'espace disponible
		gbc.anchor = GridBagConstraints.NORTH;	//Point d'ancrage
		gbc.insets = new Insets(5, 5, 5, 5);	//Margins & paddings
		gbc.weightx = 1;	//Comment répartir l'espace supplémentaire entre composants
		gbc.weighty = 1;

		addButtonsToList();
		addButtonsToPane();

		deleteButton.setPreferredSize(new Dimension(30, 10));
		deleteButton.setBorder(BorderFactory.createBevelBorder(0, new Color(150,150,150), new Color(150,150,150)));
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(editor.getZoneDeDessin().getDessinSelect()!=null)
				{
					System.out.println("entro a borrar");
				editor.deleteDessin(editor.getZoneDeDessin().getDessinSelect().getName());
				try {
					
					editor.getFrame().getServer().deleteDessin(editor.getZoneDeDessin().getDessinSelect().getName());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				else {
					JOptionPane.showMessageDialog(editor, "Please select a shape", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		for (SizeButton button : buttonList) {
			button.setPreferredSize(new Dimension(30, 10));
			button.setBorder(BorderFactory.createBevelBorder(0, new Color(150,150,150), new Color(150,150,150)));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					newSize = button.getRadius();
					newShape = button.getShape();
					sizeChanged();
					shapeChanged();
					button.setBorder(BorderFactory.createBevelBorder(1, new Color(105,105,105), new Color(120,120,120)));
					for (SizeButton other : buttonList) {
						if (other != button) {
							other.setBorder(BorderFactory.createBevelBorder(0, new Color(150,150,150), new Color(150,150,150)));
						}
					}
				}
			});
		}
	}

	public int getRadius() {
		return newSize;
	}

	public void setSize(int newSize) {
		this.newSize = newSize;
	}

	public void sizeChanged() {
		editor.getZoneDeDessin().setRadius(newSize);
	}

	public String getShape() {
		return newShape;
	}

	public void setShape(String newShape) {
		this.newShape = newShape;
	}

	public void shapeChanged() {
		editor.getZoneDeDessin().setShape(newShape);
	}

	public void addButtonsToList() {
		sizeGroup.add(rectButton);
		sizeGroup.add(ellipseButton);
		sizeGroup.add(deleteButton);
		sizeGroup.add(smallButton);
		sizeGroup.add(mediumButton);
		sizeGroup.add(bigButton);
		buttonList.add(rectButton);
		buttonList.add(ellipseButton);
		buttonList.add(smallButton);
		buttonList.add(mediumButton);
		buttonList.add(bigButton);
	}

	public void addButtonsToPane() {
		gbc.gridx = 0;
		gbc.gridy = 0;  
		add(new JLabel("Lines"), gbc);
		gbc.gridx++;
		add(smallButton, gbc);
		gbc.gridx++;
		add(mediumButton, gbc);
		gbc.gridx++;
		add(bigButton, gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		add(new JLabel("Shapes"), gbc);
		gbc.gridx++;
		add(rectButton, gbc);
		gbc.gridx++;
		add(ellipseButton, gbc);
		gbc.gridx++;
		add(deleteButton, gbc);
		
	}

	class SizeButton extends JButton {
		private static final long serialVersionUID = 1L;
		String shape;
		int size;

		public SizeButton (String shape, int size) {
			String imageName;
			this.shape = shape;
			this.size = size;
			setToolTipText(shape);

			if (size == 0) {
				imageName = shape + ".png";				
			} else {
				imageName = shape + size+ ".png";				
			}
			if(shape.equals("Delete")) {
				imageName = shape + ".jpg";				
			}
			try {
				Image shapeImage = ImageIO.read(getClass().getResource(imageName));
				Image resizedImage = shapeImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH); // Bords lisses
				setIcon(new ImageIcon(resizedImage));
			} catch (Exception ex) {
				setEnabled(false);
			}
		}

		public int getRadius() {
			return size;
		}

		public String getShape() {
			return shape;
		}
	}

}
