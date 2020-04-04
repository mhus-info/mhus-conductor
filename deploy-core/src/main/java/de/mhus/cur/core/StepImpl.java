package de.mhus.cur.core;

import java.util.LinkedList;
import java.util.Map;

import de.mhus.cur.api.Conductor;
import de.mhus.cur.api.Context;
import de.mhus.cur.api.Labels;
import de.mhus.cur.api.Step;
import de.mhus.lib.core.MString;
import de.mhus.lib.core.MSystem;
import de.mhus.lib.core.matcher.Condition;
import de.mhus.lib.errors.MException;
import de.mhus.lib.errors.MRuntimeException;

public class StepImpl implements Step {

	protected LinkedList<String> parameters;
	protected LabelsImpl selector;
	protected String order;
	protected boolean orderAsc = true;
	protected String target;
    protected Conductor cur;
    protected String condition;
    protected String ident;

    @Override
    public LinkedList<String> getParameters() {
        return parameters;
    }

    @Override
    public Labels getSelector() {
        return selector;
    }

    @Override
    public String getOrder() {
        return order;
    }

    @Override
    public boolean isOrderAsc() {
        return orderAsc;
    }

    @Override
    public String getTarget() {
        return target;
    }

    public void init(Conductor cur, String ident) {
        this.cur = cur;
        this.ident = ident;
    }

    @Override
	public String toString() {
		return MSystem.toString(this, ident);
	}

	@Override
	public String getCondition() {
		return condition;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean matchCondition(Context context) {
		String condStr = getCondition();
		if (MString.isEmptyTrim(condStr)) return true;
		if (condStr.equals("skip")) return false;
		
		try {
			Condition filter = new Condition(condStr);
			return filter.matches((Map<String, ?>) context.getProperties());
		} catch (MException e) {
			throw new MRuntimeException(this,condStr,e);
		}
	}
	
}
