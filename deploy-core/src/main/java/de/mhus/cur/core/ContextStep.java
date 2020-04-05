package de.mhus.cur.core;

import java.util.LinkedList;
import java.util.Map.Entry;

import de.mhus.cur.api.Context;
import de.mhus.cur.api.Labels;
import de.mhus.cur.api.Step;
import de.mhus.lib.core.IProperties;
import de.mhus.lib.core.MProperties;

public class ContextStep implements Step {

	private ContextImpl context;
	private Step inst;
	private ContextLabels selector;
	LinkedList<String> parameters;
	private MProperties properties;
	
	public ContextStep(ContextImpl context, Step inst) {
		this.context = context;
		this.inst = inst;
	}

	@Override
	public LinkedList<String> getArguments() {
		if (parameters == null) {
			parameters = new LinkedList<>();
			inst.getArguments().forEach(v -> parameters.add( context.make(v) ));
		}
		return parameters;
	}

	@Override
	public Labels getSelector() {
		if (selector == null)
			selector = new ContextLabels(context, inst.getSelector());
		return selector;
	}

	@Override
	public String getOrder() {
		return context.make(inst.getOrder());
	}

	@Override
	public boolean isOrderAsc() {
		return inst.isOrderAsc();
	}

	@Override
	public String getTarget() {
		return inst.getTarget();
	}

	@Override
	public String getCondition() {
		return inst.getCondition();
	}

	@Override
	public boolean matchCondition(Context context) {
		return inst.matchCondition(context);
	}

    @Override
	public String toString() {
		return inst.toString();
	}

	@Override
	public String getTitle() {
		return context.make(inst.getTitle());
	}

	@Override
	public IProperties getProperties() {
		if (properties == null) {
			properties = new MProperties();
			for (Entry<String, Object> entry : inst.getProperties().entrySet()) {
				Object v = entry.getValue();
				if (v instanceof String)
					v = context.make((String)v);
				properties.put(entry.getKey(), v);
			}
		}
		return properties;
	}

}
