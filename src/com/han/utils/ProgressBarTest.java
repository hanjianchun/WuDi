package com.han.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class ProgressBarTest {
	public void show_ProgressBar(Composite parent) {

		ProgressBar hBar1 = new ProgressBar(parent, SWT.HORIZONTAL | SWT.INDETERMINATE | SWT.NO_TRIM);
		hBar1.setMinimum(0);
		hBar1.setMaximum(10);
		(new IncresingOperator(hBar1)).start();

	}

	class IncresingOperator extends Thread {
		private ProgressBar bar;

		IncresingOperator(ProgressBar bar) {
			this.bar = bar;
		}

		public void run() {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (bar.isDisposed())
						return;
					for (int i = 0; i < bar.getMaximum(); i++) {
						System.out.println(">>>>>>>>>>>>>>" + i);
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						bar.setSelection(bar.getSelection() + 1);
//						if (i == 29) {
//							bar.dispose();
//						}
					}
				}
			});
		}
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("ProgressBar Test:");
		shell.setLayout(new FillLayout(SWT.VERTICAL));

		(new ProgressBarTest()).show_ProgressBar(shell);

		shell.pack();
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		display.dispose();

	}
}