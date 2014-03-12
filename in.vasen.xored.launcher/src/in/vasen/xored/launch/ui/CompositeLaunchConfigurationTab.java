package in.vasen.xored.launch.ui;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class CompositeLaunchConfigurationTab extends
		AbstractLaunchConfigurationTab implements ILaunchConfigurationTab {

	private TableViewer tableViewer;
	
	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));

		tableViewer = new TableViewer(comp, SWT.SINGLE);
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Composite buttons = new Composite(comp, SWT.None);
		buttons.setLayout(new GridLayout());

		Button addButton = new Button(buttons, SWT.PUSH);
		addButton.setText("Add...");
		addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Button removeButton = new Button(buttons, SWT.PUSH);
		removeButton.setText("Remove");
		removeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				
		setControl(comp);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Composite";
	}

}
