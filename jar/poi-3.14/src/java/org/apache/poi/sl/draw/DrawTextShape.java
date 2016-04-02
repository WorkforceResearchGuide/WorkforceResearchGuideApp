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

package org.apache.poi.sl.draw;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import org.apache.poi.sl.usermodel.Insets2D;
import org.apache.poi.sl.usermodel.PlaceableShape;
import org.apache.poi.sl.usermodel.ShapeContainer;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextParagraph.BulletStyle;
import org.apache.poi.sl.usermodel.TextRun;
import org.apache.poi.sl.usermodel.TextShape;

public class DrawTextShape extends DrawSimpleShape {

    public DrawTextShape(TextShape<?,?> shape) {
        super(shape);
    }

    @Override
    public void drawContent(Graphics2D graphics) {
        DrawFactory.getInstance(graphics).fixFonts(graphics);
        
        TextShape<?,?> s = getShape();
        
        Rectangle2D anchor = DrawShape.getAnchor(graphics, s);
        Insets2D insets = s.getInsets();
        double x = anchor.getX() + insets.left;
        double y = anchor.getY();

        // remember the initial transform
        AffineTransform tx = graphics.getTransform();

        // Transform of text in flipped shapes is special.
        // At this point the flip and rotation transform is already applied
        // (see DrawShape#applyTransform ), but we need to restore it to avoid painting "upside down".
        // See Bugzilla 54210.

        boolean vertFlip = s.getFlipVertical();
        boolean horzFlip = s.getFlipHorizontal();
        ShapeContainer<?,?> sc = s.getParent();
        while (sc instanceof PlaceableShape) {
            PlaceableShape<?,?> ps = (PlaceableShape<?,?>)sc;
            vertFlip ^= ps.getFlipVertical();
            horzFlip ^= ps.getFlipHorizontal();
            sc = ps.getParent();
        }
        
        // Horizontal flipping applies only to shape outline and not to the text in the shape.
        // Applying flip second time restores the original not-flipped transform
        if (horzFlip ^ vertFlip) {
            graphics.translate(anchor.getX() + anchor.getWidth(), anchor.getY());
            graphics.scale(-1, 1);
            graphics.translate(-anchor.getX(), -anchor.getY());
        }
        
        Double textRot = s.getTextRotation();
        if (textRot != null && textRot != 0) {
            graphics.translate(anchor.getCenterX(), anchor.getCenterY());
            graphics.rotate(Math.toRadians(textRot));
            graphics.translate(-anchor.getCenterX(), -anchor.getCenterY());
        }

        // first dry-run to calculate the total height of the text
        double textHeight = s.getTextHeight();

        switch (s.getVerticalAlignment()){
            default:
            case TOP:
                y += insets.top;
                break;
            case BOTTOM:
                y += anchor.getHeight() - textHeight - insets.bottom;
                break;
            case MIDDLE:
                double delta = anchor.getHeight() - textHeight - insets.top - insets.bottom;
                y += insets.top + delta/2;
                break;
        }

        drawParagraphs(graphics, x, y);

        // restore the transform
        graphics.setTransform(tx);
    }

    /**
     * paint the paragraphs starting from top left (x,y)
     *
     * @return  the vertical advance, i.e. the cumulative space occupied by the text
     */
    public double drawParagraphs(Graphics2D graphics, double x, double y) {
        DrawFactory fact = DrawFactory.getInstance(graphics);

        double y0 = y;
        //noinspection RedundantCast
        Iterator<? extends TextParagraph<?,?,? extends TextRun>> paragraphs =
            (Iterator<? extends TextParagraph<?,?,? extends TextRun>>) getShape().iterator();
        
        boolean isFirstLine = true;
        for (int autoNbrIdx=0; paragraphs.hasNext(); autoNbrIdx++){
            TextParagraph<?,?,? extends TextRun> p = paragraphs.next();
            DrawTextParagraph dp = fact.getDrawable(p);
            BulletStyle bs = p.getBulletStyle();
            if (bs == null || bs.getAutoNumberingScheme() == null) {
                autoNbrIdx = -1;
            } else {
                Integer startAt = bs.getAutoNumberingStartAt();
                if (startAt == null) startAt = 1;
                // TODO: handle reset auto number indexes
                if (startAt > autoNbrIdx) autoNbrIdx = startAt;
            }
            dp.setAutoNumberingIdx(autoNbrIdx);
            dp.breakText(graphics);

            if (!isFirstLine) {
                // the amount of vertical white space before the paragraph
                Double spaceBefore = p.getSpaceBefore();
                if (spaceBefore == null) spaceBefore = 0d;
                if(spaceBefore > 0) {
                    // positive value means percentage spacing of the height of the first line, e.g.
                    // the higher the first line, the bigger the space before the paragraph
                    y += spaceBefore*0.01*dp.getFirstLineHeight();
                } else {
                    // negative value means the absolute spacing in points
                    y += -spaceBefore;
                }
            }
            isFirstLine = false;
            
            dp.setPosition(x, y);
            dp.draw(graphics);
            y += dp.getY();

            if (paragraphs.hasNext()) {
                Double spaceAfter = p.getSpaceAfter();
                if (spaceAfter == null) spaceAfter = 0d;
                if(spaceAfter > 0) {
                    // positive value means percentage spacing of the height of the last line, e.g.
                    // the higher the last line, the bigger the space after the paragraph
                    y += spaceAfter*0.01*dp.getLastLineHeight();
                } else {
                    // negative value means the absolute spacing in points
                    y += -spaceAfter;
                }
            }
        }
        return y - y0;
    }

    /**
     * Compute the cumulative height occupied by the text
     */
    public double getTextHeight(){
        // dry-run in a 1x1 image and return the vertical advance
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();
        DrawFactory.getInstance(graphics).fixFonts(graphics);
        return drawParagraphs(graphics, 0, 0);
    }
    
    @Override
    protected TextShape<?,?> getShape() {
        return (TextShape<?,?>)shape;
    }
}
