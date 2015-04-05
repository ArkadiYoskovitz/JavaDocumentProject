package app;

import java.awt.EventQueue;

import controller.ViewController;

public class AppDelegate {

	// Attribute
	private static ViewController mainViewController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setMainViewController(new ViewController());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static ViewController getMainViewController() {
		return mainViewController;
	}

	public static void setMainViewController(ViewController mainViewController) {
		AppDelegate.mainViewController = mainViewController;
	}

}
