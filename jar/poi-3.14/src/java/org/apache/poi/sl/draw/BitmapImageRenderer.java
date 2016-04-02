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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;

/**
 * For now this class renders only images supported by the javax.imageio.ImageIO framework.
 **/
public class BitmapImageRenderer implements ImageRenderer {
    private final static POILogger LOG = POILogFactory.getLogger(ImageRenderer.class);

    protected BufferedImage img;

    @Override
    public void loadImage(InputStream data, String contentType) throws IOException {
        img = convertBufferedImage(ImageIO.read(data), contentType);
    }

    @Override
    public void loadImage(byte data[], String contentType) throws IOException {
        img = convertBufferedImage(ImageIO.read(new ByteArrayInputStream(data)), contentType);
    }

    /**
     * Add alpha channel to buffered image
     */
    private static BufferedImage convertBufferedImage(BufferedImage img, String contentType) {
        if (img == null) {
            LOG.log(POILogger.WARN, "Content-type: "+contentType+" is not support. Image ignored.");
            return null;
        }

        BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return bi;
    }


    /**
     * @return the buffered image
     */
    public BufferedImage getImage() {
        return img;
    }

    @Override
    public Dimension getDimension() {
        return (img == null)
            ? new Dimension(0,0)
            : new Dimension(img.getWidth(),img.getHeight());
    }

    @Override
    public void setAlpha(double alpha) {
        if (img == null) return;

        Dimension dim = getDimension();
        BufferedImage newImg = new BufferedImage((int)dim.getWidth(), (int)dim.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImg.createGraphics();
        RescaleOp op = new RescaleOp(new float[]{1.0f, 1.0f, 1.0f, (float)alpha}, new float[]{0,0,0,0}, null);
        g.drawImage(img, op, 0, 0);
        g.dispose();

        img = newImg;
    }


    @Override
    public boolean drawImage(
        Graphics2D graphics,
        Rectangle2D anchor) {
        return drawImage(graphics, anchor, null);
    }

    @Override
    public boolean drawImage(
        Graphics2D graphics,
        Rectangle2D anchor,
        Insets clip) {
        if (img == null) return false;

        boolean isClipped = true;
        if (clip == null) {
            isClipped = false;
            clip = new Insets(0,0,0,0);
        }

        int iw = img.getWidth();
        int ih = img.getHeight();


        double cw = (100000-clip.left-clip.right) / 100000.0;
        double ch = (100000-clip.top-clip.bottom) / 100000.0;
        double sx = anchor.getWidth()/(iw*cw);
        double sy = anchor.getHeight()/(ih*ch);
        double tx = anchor.getX()-(iw*sx*clip.left/100000.0);
        double ty = anchor.getY()-(ih*sy*clip.top/100000.0);

        AffineTransform at = new AffineTransform(sx, 0, 0, sy, tx, ty) ;

        Shape clipOld = graphics.getClip();
        if (isClipped) graphics.clip(anchor.getBounds2D());
        graphics.drawRenderedImage(img, at);
        graphics.setClip(clipOld);

        return true;
    }
}
