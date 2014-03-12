package in.vasen.xored.launch.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class CompositeLaunchConfigurationTab extends
		AbstractLaunchConfigurationTab implements ILaunchConfigurationTab {

	private TableViewer tableViewer;
	private List<String> items = new ArrayList<String>();
	
	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));

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

		Button addButton = new Button(buttons, SWT.PUSH);
		addButton.setText("Add...");
		addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				items.add("qqq");
				tableViewer.refresh(true);
				updateLaunchConfigurationDialog();
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

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		items.clear();
		items.add("123");
		items.add("456");
		configuration.setAttribute("items", items);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			items = (ArrayList<String>) configuration.getAttribute("items",  items);
		} catch (CoreException e) {
			// nothing
		}
		tableViewer.setInput(items);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("items", items);
	}

	@Override
	public String getName() {
		return "Composite";
	}

}
