/*
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * This software is open source.
 * See the bottom of this file for the licence.
 */

package com.example.alldocument.library.fc.dom4j.rule;

import java.util.HashMap;

import com.example.alldocument.library.fc.dom4j.Document;
import com.example.alldocument.library.fc.dom4j.Element;
import com.example.alldocument.library.fc.dom4j.Node;
import com.example.alldocument.library.fc.dom4j.rule.Action;
import com.example.alldocument.library.fc.dom4j.rule.Mode;
import com.example.alldocument.library.fc.dom4j.rule.Pattern;
import com.example.alldocument.library.fc.dom4j.rule.Rule;
import com.example.alldocument.library.fc.dom4j.rule.pattern.NodeTypePattern;


/**
 * <p>
 * <code>RuleManager</code> manages a set of rules such that a rule can be
 * found for a given DOM4J Node using the XSLT processing model.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class RuleManager
{
    /** Map of modes indexed by mode */
    private HashMap modes = new HashMap();

    /**
     * A counter so that rules can be ordered by the order in which they were
     * added to the rule base
     */
    private int appearenceCount;

    /** Holds value of property valueOfAction. */
    private com.example.alldocument.library.fc.dom4j.rule.Action valueOfAction;

    public RuleManager()
    {
    }

    /**
     * DOCUMENT ME!
     * 
     * @param modeName
     *            DOCUMENT ME!
     * 
     * @return the Mode instance for the given mode name. If one does not exist
     *         then it will be created.
     */
    public com.example.alldocument.library.fc.dom4j.rule.Mode getMode(String modeName)
    {
        com.example.alldocument.library.fc.dom4j.rule.Mode mode = (com.example.alldocument.library.fc.dom4j.rule.Mode)modes.get(modeName);

        if (mode == null)
        {
            mode = createMode();
            modes.put(modeName, mode);
        }

        return mode;
    }

    public void addRule(com.example.alldocument.library.fc.dom4j.rule.Rule rule)
    {
        rule.setAppearenceCount(++appearenceCount);

        com.example.alldocument.library.fc.dom4j.rule.Mode mode = getMode(rule.getMode());
        com.example.alldocument.library.fc.dom4j.rule.Rule[] childRules = rule.getUnionRules();

        if (childRules != null)
        {
            for (int i = 0, size = childRules.length; i < size; i++)
            {
                mode.addRule(childRules[i]);
            }
        }
        else
        {
            mode.addRule(rule);
        }
    }

    public void removeRule(com.example.alldocument.library.fc.dom4j.rule.Rule rule)
    {
        com.example.alldocument.library.fc.dom4j.rule.Mode mode = getMode(rule.getMode());
        com.example.alldocument.library.fc.dom4j.rule.Rule[] childRules = rule.getUnionRules();

        if (childRules != null)
        {
            for (int i = 0, size = childRules.length; i < size; i++)
            {
                mode.removeRule(childRules[i]);
            }
        }
        else
        {
            mode.removeRule(rule);
        }
    }

    /**
     * Performs an XSLT processing model match for the rule which matches the
     * given Node the best.
     * 
     * @param modeName
     *            is the name of the mode associated with the rule if any
     * @param node
     *            is the DOM4J Node to match against
     * 
     * @return the matching Rule or no rule if none matched
     */
    public com.example.alldocument.library.fc.dom4j.rule.Rule getMatchingRule(String modeName, Node node)
    {
        com.example.alldocument.library.fc.dom4j.rule.Mode mode = (com.example.alldocument.library.fc.dom4j.rule.Mode)modes.get(modeName);

        if (mode != null)
        {
            return mode.getMatchingRule(node);
        }
        else
        {
            System.out.println("Warning: No Mode for mode: " + mode);

            return null;
        }
    }

    public void clear()
    {
        modes.clear();
        appearenceCount = 0;
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     * 
     * @return the default value-of action which is used in the default rules
     *         for the pattern "text()|&#64;"
     */
    public com.example.alldocument.library.fc.dom4j.rule.Action getValueOfAction()
    {
        return valueOfAction;
    }

    /**
     * Sets the default value-of action which is used in the default rules for
     * the pattern "text()|&#64;"
     * 
     * @param valueOfAction
     *            DOCUMENT ME!
     */
    public void setValueOfAction(com.example.alldocument.library.fc.dom4j.rule.Action valueOfAction)
    {
        this.valueOfAction = valueOfAction;
    }

    // Implementation methods
    // -------------------------------------------------------------------------

    /**
     * A factory method to return a new {@link com.example.alldocument.library.fc.dom4j.rule.Mode}instance which should add
     * the necessary default rules
     * 
     * @return DOCUMENT ME!
     */
    protected com.example.alldocument.library.fc.dom4j.rule.Mode createMode()
    {
        com.example.alldocument.library.fc.dom4j.rule.Mode mode = new com.example.alldocument.library.fc.dom4j.rule.Mode();
        addDefaultRules(mode);

        return mode;
    }

    /**
     * Adds the default stylesheet rules to the given {@link com.example.alldocument.library.fc.dom4j.rule.Mode}instance
     * 
     * @param mode
     *            DOCUMENT ME!
     */
    protected void addDefaultRules(final com.example.alldocument.library.fc.dom4j.rule.Mode mode)
    {
        // add an apply templates rule
        com.example.alldocument.library.fc.dom4j.rule.Action applyTemplates = new com.example.alldocument.library.fc.dom4j.rule.Action()
        {
            public void run(Node node) throws Exception
            {
                if (node instanceof Element)
                {
                    mode.applyTemplates((Element)node);
                }
                else if (node instanceof Document)
                {
                    mode.applyTemplates((Document)node);
                }
            }
        };

        com.example.alldocument.library.fc.dom4j.rule.Action valueOf = getValueOfAction();

        addDefaultRule(mode, NodeTypePattern.ANY_DOCUMENT, applyTemplates);
        addDefaultRule(mode, NodeTypePattern.ANY_ELEMENT, applyTemplates);

        if (valueOf != null)
        {
            addDefaultRule(mode, NodeTypePattern.ANY_ATTRIBUTE, valueOf);
            addDefaultRule(mode, NodeTypePattern.ANY_TEXT, valueOf);
        }
    }

    protected void addDefaultRule(Mode mode, com.example.alldocument.library.fc.dom4j.rule.Pattern pattern, com.example.alldocument.library.fc.dom4j.rule.Action action)
    {
        com.example.alldocument.library.fc.dom4j.rule.Rule rule = createDefaultRule(pattern, action);
        mode.addRule(rule);
    }

    protected com.example.alldocument.library.fc.dom4j.rule.Rule createDefaultRule(Pattern pattern, Action action)
    {
        com.example.alldocument.library.fc.dom4j.rule.Rule rule = new Rule(pattern, action);
        rule.setImportPrecedence(-1);

        return rule;
    }
}

/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided that the
 * following conditions are met:
 * 
 * 1. Redistributions of source code must retain copyright statements and
 * notices. Redistributions must also contain a copy of this document.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The name "DOM4J" must not be used to endorse or promote products derived
 * from this Software without prior written permission of MetaStuff, Ltd. For
 * written permission, please contact dom4j-info@metastuff.com.
 * 
 * 4. Products derived from this Software may not be called "DOM4J" nor may
 * "DOM4J" appear in their names without prior written permission of MetaStuff,
 * Ltd. DOM4J is a registered trademark of MetaStuff, Ltd.
 * 
 * 5. Due credit should be given to the DOM4J Project - http://www.dom4j.org
 * 
 * THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL METASTUFF, LTD. OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 */
