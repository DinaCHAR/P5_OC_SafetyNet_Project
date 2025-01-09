package com.openclassroom.api.model;

import java.util.List;
import java.util.Map;

public class ChildAlert {

    private List<Map<String, Object>> children; // Liste des enfants
    private List<Map<String, Object>> householdMembers; // Liste des membres du foyer

    public ChildAlert() {
    }

    public List<Map<String, Object>> getChildren() {
        return children;
    }

    public void setChildren(List<Map<String, Object>> children) {
        this.children = children;
    }

    public List<Map<String, Object>> getHouseholdMembers() {
        return householdMembers;
    }

    public void setHouseholdMembers(List<Map<String, Object>> householdMembers) {
        this.householdMembers = householdMembers;
    }
}
