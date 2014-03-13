package in.vasen.xored.launch;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

public class CompositeLaunchConfigurationDelegate implements
		ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		@SuppressWarnings("unchecked")
		ArrayList<String> items = (ArrayList<String>) configuration.getAttribute("items", new ArrayList<String>());
		ArrayList<ILaunchConfiguration> configs = fetchLaunchConfigurations(items, mode);
		for (ILaunchConfiguration config : configs) {
//			config.launch(mode, monitor);
		}
	}
	
	private ArrayList<ILaunchConfiguration> fetchLaunchConfigurations(ArrayList<String> names, String mode) {
		ArrayList<ILaunchConfiguration> launchConifigs = new ArrayList<ILaunchConfiguration>();
		for (String name : names) {
			try {
				ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations();
				for (ILaunchConfiguration config : configs) {
					if (config.supportsMode(mode) && config.getName().equals(name)) {
						launchConifigs.add(config);
					}
				}
			} catch (CoreException e) {
			}
		}
		return launchConifigs;
	}
	
}
