package in.vasen.xored.launch.ui;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationFilteredTree;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchGroupFilter;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.dialogs.PatternFilter;

@SuppressWarnings("restriction")
public class CompositeLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	private TableViewer tableViewer;
	private ArrayList<String> items = new ArrayList<String>();
	private String mode;
	private LaunchConfigurationFilteredTree tree;
	
	public CompositeLaunchConfigurationTab(String mode) {
		this.mode = mode;
	}

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(3, false));

		tableViewer = new TableViewer(comp, SWT.SINGLE | SWT.BORDER);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		
		TableViewerColumn colItems = new TableViewerColumn(tableViewer, SWT.NONE);
		colItems.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return element.toString();
			}	
		});
		colItems.getColumn().setWidth(200);
		colItems.getColumn().setText("Launch config items");
	
		
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Composite buttons = new Composite(comp, SWT.None);
		buttons.setLayout(new GridLayout());

		ILaunchGroup launchGroup = getLaunchGroup(this.mode);
		tree = new LaunchConfigurationFilteredTree(comp, SWT.SINGLE | SWT.BORDER, new PatternFilter(), launchGroup, null);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tree.createViewControl();
		fixFilters(tree);
		
		Button addButton = new Button(buttons, SWT.PUSH);
		addButton.setText("Add...");
		addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				StructuredSelection sel = (StructuredSelection) tree.getViewer().getSelection();
				Object item = sel.getFirstElement();
				if (item instanceof ILaunchConfiguration) {
					items.add(((ILaunchConfiguration) item).getName());
					tableViewer.refresh(true);
					updateLaunchConfigurationDialog();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing
			}
		
		});
		
		Button removeButton = new Button(buttons, SWT.PUSH);
		removeButton.setText("Remove");
		removeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		removeButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Object sel = ((StructuredSelection) tableViewer.getSelection()).getFirstElement();
				if (sel != null) {
					items.remove(sel);
					tableViewer.refresh(true);
					updateLaunchConfigurationDialog();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing
			}
		});
				
		setControl(comp);
	}

	private void fixFilters(LaunchConfigurationFilteredTree tree) {
		ViewerFilter[] filters = tree.getViewer().getFilters();
		for (ViewerFilter filter : filters) {
			if (filter instanceof LaunchGroupFilter) {
				tree.getViewer().removeFilter(filter);
			}
		}
	}
	
	private ILaunchGroup getLaunchGroup(String mode) {
		ILaunchGroup[] lGroups = DebugUIPlugin.getDefault().getLaunchConfigurationManager().getLaunchGroups();
		for (ILaunchGroup lGroup : lGroups) {
			if (lGroup.getMode().equals(mode)) {
				return lGroup;
			}
		}
		return null;
	}
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// nothing, empty config is the default one
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			ArrayList<String> itemConfig = (ArrayList<String>) configuration.getAttribute("items",  items);
			items = (ArrayList<String>) itemConfig.clone();
		} catch (CoreException e) {
			// nothing
		}
		tableViewer.setInput(items);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		@SuppressWarnings("unchecked")
		ArrayList<String> itemConfig = (ArrayList<String>) items.clone();
		configuration.setAttribute("items", itemConfig);
	}

	@Override
	public String getName() {
		return "Composite";
	}

}
