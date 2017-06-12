package com.davidparkeredwards.windrosetools.model.journey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidedwards on 6/12/17.
 */

public class TagRule {

    //Operator types
    public static final int EQUAL_TO = 1;
    public static final int NOT_EQUAL_TO = 2;
    public static final int LESS_THAN = 3;
    public static final int LESS_THAN_OR_EQUAL_TO = 4;
    public static final int GREATER_THAN = 5;
    public static final int GREATER_THAN_OR_EQUAL_TO = 6;
    public static final int CONTAINS = 7;
    public static final int OR = 11;
    public static final int AND = 12;

    //Format Property operator TagRuleSet

    private String uniqueId;
    private String keyName; //Name of the key in the tagField that will be checked
    private TagRuleNode rootNode;

    public boolean applies(Tag tag) {
        return rootNode.isTrue(tag);
    }

    private class TagRuleStatement {
        private String operator;
        private String checkValue;

        public boolean isTrue(Tag tag) {
            switch(tag.getValueType()) {
                case Tag.BOOLEAN:
                    return checkBoolean(tag);

                case Tag.STRING:
                    return checkString(tag);
                case Tag.NUMBER:
                    return checkNumber(tag);
                case Tag.STRING_ARRAY:
                    return checkArray(tag);
                case Tag.NUMBER_ARRAY:
                    return checkArray(tag);
                default:
                    return false;
            }
        }

        private boolean checkBoolean(Tag tag) {
            boolean checkValueB = Boolean.getBoolean(checkValue);
            boolean tagValue = Boolean.getBoolean(tag.getValue().get(0));
            switch (Integer.valueOf(operator)) {
                case EQUAL_TO:
                    return (checkValueB == tagValue);
                case NOT_EQUAL_TO:
                    return (checkValueB != tagValue);
                default:
                    return false;
            }
        }

        private boolean checkString(Tag tag) {
            String checkValueS = checkValue;
            String tagValue = tag.getValue().get(0);
            switch (Integer.valueOf(operator)) {
                case EQUAL_TO:
                    return (checkValueS.equals(tagValue));
                case NOT_EQUAL_TO:
                    return (!checkValueS.equals(tagValue));
                default:
                    return false;
            }
        }

        private boolean checkNumber(Tag tag) {
            Double checkValueS = Double.valueOf(checkValue);
            Double tagValue = Double.valueOf(tag.getValue().get(0));
            switch (Integer.valueOf(operator)) {
                case EQUAL_TO:
                    return (checkValueS.equals(tagValue));
                case NOT_EQUAL_TO:
                    return (!checkValueS.equals(tagValue));
                case LESS_THAN:
                    return (tagValue < checkValueS);
                case LESS_THAN_OR_EQUAL_TO:
                    return (tagValue <= checkValueS);
                case GREATER_THAN:
                    return (tagValue > checkValueS);
                case GREATER_THAN_OR_EQUAL_TO:
                    return (tagValue >= checkValueS);
                default:
                    return false;
            }
        }

        private boolean checkArray(Tag tag) {
            String checkValueS = checkValue;
            List<String> tagValue = tag.getValue();
            if (Integer.valueOf(operator) == CONTAINS) {
                return tagValue.contains(checkValueS);
            } else {
                return false;
            }
        }
    }

    //Or nodes with And lists
    private class TagRuleNode {

        private ArrayList<TagRuleNode> andNodes; //Nodes
        private ArrayList<TagRuleNode> orNodes;
        private TagRuleStatement tagRuleStatement; //Conditional Statements

        public TagRuleNode() {
            andNodes = new ArrayList<>();
            orNodes = new ArrayList<>();
        }

        public boolean isTrue(Tag tag) {
            if (tagRuleStatement.isTrue(tag)) {
                for(TagRuleNode andNode : andNodes) {
                    if(!andNode.isTrue(tag)) return false;
                }
                return true;
            } else {
                for (TagRuleNode orNode : orNodes) {
                    if(orNode.isTrue(tag)) return true;
                }
                return false;
            }
        }
    }
}
