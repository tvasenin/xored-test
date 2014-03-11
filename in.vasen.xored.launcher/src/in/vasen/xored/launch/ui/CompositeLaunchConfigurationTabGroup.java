package in.vasen.xored.launch.ui;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class CompositeLaunchConfigurationTabGroup extends
		AbstractLaunchConfigurationTabGroup {

	public CompositeLaunchConfigurationTabGroup() {
		// empty
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		// TODO Auto-generated method stub
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
				new CommonTab()
		};
		setTabs(tabs);
	}

}
