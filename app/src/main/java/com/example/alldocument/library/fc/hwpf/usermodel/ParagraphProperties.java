/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package com.example.alldocument.library.fc.hwpf.usermodel;

import com.example.alldocument.library.fc.hwpf.model.types.PAPAbstractType;
import com.example.alldocument.library.fc.hwpf.usermodel.BorderCode;
import com.example.alldocument.library.fc.hwpf.usermodel.DropCapSpecifier;
import com.example.alldocument.library.fc.hwpf.usermodel.LineSpacingDescriptor;
import com.example.alldocument.library.fc.hwpf.usermodel.ShadingDescriptor;

public final class ParagraphProperties extends PAPAbstractType implements Cloneable
{

    private boolean jcLogical = false;
    //
    private short tabClearPosition;
    
    public ParagraphProperties()
    {
        setAnld(new byte[84]);
        setPhe(new byte[12]);
    }

    public Object clone() throws CloneNotSupportedException
    {
        ParagraphProperties pp = (ParagraphProperties)super.clone();
        pp.setAnld(getAnld().clone());
        pp.setBrcTop((com.example.alldocument.library.fc.hwpf.usermodel.BorderCode)getBrcTop().clone());
        pp.setBrcLeft((com.example.alldocument.library.fc.hwpf.usermodel.BorderCode)getBrcLeft().clone());
        pp.setBrcBottom((com.example.alldocument.library.fc.hwpf.usermodel.BorderCode)getBrcBottom().clone());
        pp.setBrcRight((com.example.alldocument.library.fc.hwpf.usermodel.BorderCode)getBrcRight().clone());
        pp.setBrcBetween((com.example.alldocument.library.fc.hwpf.usermodel.BorderCode)getBrcBetween().clone());
        pp.setBrcBar((com.example.alldocument.library.fc.hwpf.usermodel.BorderCode)getBrcBar().clone());
        pp.setDcs(getDcs().clone());
        pp.setLspd((LineSpacingDescriptor)getLspd().clone());
        pp.setShd((com.example.alldocument.library.fc.hwpf.usermodel.ShadingDescriptor)getShd().clone());
        pp.setPhe(getPhe().clone());
        return pp;
    }

    public com.example.alldocument.library.fc.hwpf.usermodel.BorderCode getBarBorder()
    {
        return super.getBrcBar();
    }

    public com.example.alldocument.library.fc.hwpf.usermodel.BorderCode getBottomBorder()
    {
        return super.getBrcBottom();
    }

    public com.example.alldocument.library.fc.hwpf.usermodel.DropCapSpecifier getDropCap()
    {
        return super.getDcs();
    }

    public int getFirstLineIndent()
    {
        return super.getDxaLeft1();
    }

    public int getFontAlignment()
    {
        return super.getWAlignFont();
    }

    public int getIndentFromLeft()
    {
        return super.getDxaLeft();
    }

    public int getIndentFromRight()
    {
        return super.getDxaRight();
    }

    public int getJustification()
    {
        if (jcLogical)
        {
            if (!getFBiDi())
                return getJc();

            switch (getJc())
            {
                case 0:
                    return 2;
                case 2:
                    return 0;
                default:
                    return getJc();
            }
        }

        return getJc();
    }

    public com.example.alldocument.library.fc.hwpf.usermodel.BorderCode getLeftBorder()
    {
        return super.getBrcLeft();
    }

    public LineSpacingDescriptor getLineSpacing()
    {
        return super.getLspd();
    }

    public com.example.alldocument.library.fc.hwpf.usermodel.BorderCode getRightBorder()
    {
        return super.getBrcRight();
    }

    public com.example.alldocument.library.fc.hwpf.usermodel.ShadingDescriptor getShading()
    {
        return super.getShd();
    }

    public int getSpacingAfter()
    {
        return super.getDyaAfter();
    }

    public int getSpacingBefore()
    {
        return super.getDyaBefore();
    }

    public com.example.alldocument.library.fc.hwpf.usermodel.BorderCode getTopBorder()
    {
        return super.getBrcTop();
    }

    public boolean isAutoHyphenated()
    {
        return !super.getFNoAutoHyph();
    }

    public boolean isBackward()
    {
        return super.isFBackward();
    }

    public boolean isKinsoku()
    {
        return super.getFKinsoku();
    }

    public boolean isLineNotNumbered()
    {
        return super.getFNoLnn();
    }

    public boolean isSideBySide()
    {
        return super.getFSideBySide();
    }

    public boolean isVertical()
    {
        return super.isFVertical();
    }

    public boolean isWidowControlled()
    {
        return super.getFWidowControl();
    }

    public boolean isWordWrapped()
    {
        return super.getFWordWrap();
    }

    public boolean keepOnPage()
    {
        return super.getFKeep();
    }

    public boolean keepWithNext()
    {
        return super.getFKeepFollow();
    }

    public boolean pageBreakBefore()
    {
        return super.getFPageBreakBefore();
    }

    public void setAutoHyphenated(boolean auto)
    {
        super.setFNoAutoHyph(!auto);
    }

    public void setBackward(boolean bward)
    {
        super.setFBackward(bward);
    }

    public void setBarBorder(com.example.alldocument.library.fc.hwpf.usermodel.BorderCode bar)
    {
        super.setBrcBar(bar);
    }

    public void setBottomBorder(com.example.alldocument.library.fc.hwpf.usermodel.BorderCode bottom)
    {
        super.setBrcBottom(bottom);
    }

    public void setDropCap(DropCapSpecifier dcs)
    {
        super.setDcs(dcs);
    }

    public void setFirstLineIndent(int first)
    {
        super.setDxaLeft1(first);
    }

    public void setFontAlignment(int align)
    {
        super.setWAlignFont(align);
    }

    public void setIndentFromLeft(int dxaLeft)
    {
        super.setDxaLeft(dxaLeft);
    }

    public void setIndentFromRight(int dxaRight)
    {
        super.setDxaRight(dxaRight);
    }

    public void setJustification(byte jc)
    {
        super.setJc(jc);
        this.jcLogical = false;
    }

    public void setJustificationLogical(byte jc)
    {
        super.setJc(jc);
        this.jcLogical = true;
    }

    public void setKeepOnPage(boolean fKeep)
    {
        super.setFKeep(fKeep);
    }

    public void setKeepWithNext(boolean fKeepFollow)
    {
        super.setFKeepFollow(fKeepFollow);
    }

    public void setKinsoku(boolean kinsoku)
    {
        super.setFKinsoku(kinsoku);
    }

    public void setLeftBorder(com.example.alldocument.library.fc.hwpf.usermodel.BorderCode left)
    {
        super.setBrcLeft(left);
    }

    public void setLineNotNumbered(boolean fNoLnn)
    {
        super.setFNoLnn(fNoLnn);
    }

    public void setLineSpacing(LineSpacingDescriptor lspd)
    {
        super.setLspd(lspd);
    }

    public void setPageBreakBefore(boolean fPageBreak)
    {
        super.setFPageBreakBefore(fPageBreak);
    }

    public void setRightBorder(com.example.alldocument.library.fc.hwpf.usermodel.BorderCode right)
    {
        super.setBrcRight(right);
    }

    public void setShading(ShadingDescriptor shd)
    {
        super.setShd(shd);
    }

    public void setSideBySide(boolean fSideBySide)
    {
        super.setFSideBySide(fSideBySide);
    }

    public void setSpacingAfter(int after)
    {
        super.setDyaAfter(after);
    }

    public void setSpacingBefore(int before)
    {
        super.setDyaBefore(before);
    }

    public void setTopBorder(BorderCode top)
    {
        super.setBrcTop(top);
    }

    public void setVertical(boolean vertical)
    {
        super.setFVertical(vertical);
    }

    public void setWidowControl(boolean widowControl)
    {
        super.setFWidowControl(widowControl);
    }

    public void setWordWrapped(boolean wrap)
    {
        super.setFWordWrap(wrap);
    }
    
    public void setTabClearPosition(short position)
    {
        this.tabClearPosition = position;
    }
    
    public short getTabClearPosition()
    {
        return this.tabClearPosition;
    }
}
