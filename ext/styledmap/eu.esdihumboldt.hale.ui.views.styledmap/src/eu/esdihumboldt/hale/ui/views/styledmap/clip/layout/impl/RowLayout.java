/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2011.
 */

package eu.esdihumboldt.hale.ui.views.styledmap.clip.layout.impl;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.mapviewer.JXMapViewer;

import eu.esdihumboldt.hale.ui.views.styledmap.clip.Clip;
import eu.esdihumboldt.hale.ui.views.styledmap.clip.impl.HorizontalClip;
import eu.esdihumboldt.hale.ui.views.styledmap.clip.layout.LayoutAugmentation;
import eu.esdihumboldt.hale.ui.views.styledmap.clip.layout.PainterLayout;
import eu.esdihumboldt.hale.ui.views.styledmap.clip.layout.extension.PainterProxy;

/**
 * Layout that organizes painters in horizontal rows.
 * @author Simon Templer
 */
public class RowLayout implements PainterLayout {

	/**
	 * Row layout augmentation
	 */
	public class RowAugmentation extends AbstractDefaultAugmentation {
		
		private final int count;

		/**
		 * Create a row layout augmentation.
		 * @param count the row count
		 */
		public RowAugmentation(int count) {
			this.count = count;
		}

		@Override
		public void doPaint(Graphics2D g, JXMapViewer map,
				List<PainterProxy> painters, int width, int height) {
			// between each pair of rows...
			for (int i = 1; i < count; i++) {
				int y = (int) (i * height / (float) count);
				
				// ..draw the name of the top painter
				if (i - 1 < painters.size()) {
					String name = painters.get(i - 1).getName();
					drawText(g, name, 
							DEFAULT_MARGIN, 
							y - DEFAULT_MARGIN);
				}
				
				// ...draw a line
				drawSplitLine(g, 0, y, width, y);
				
				// ..draw the name of the bottom painter
				if (i < painters.size()) {
					String name = painters.get(i).getName();
					drawText(g, name, 
								width - DEFAULT_MARGIN - g.getFontMetrics().stringWidth(name), 
								y + DEFAULT_MARGIN + g.getFontMetrics().getAscent());
				}
			}
		}

	}

	/**
	 * @see PainterLayout#createClips(int)
	 */
	@Override
	public List<Clip> createClips(int count) {
		List<Clip> clips = new ArrayList<Clip>(count);
		float fCount = count;
		
		for (int i = 0; i < count; i++) {
			float start = i / fCount;
			float end = (i + 1) / fCount;
			
			clips.add(new HorizontalClip(start, end));
		}
		
		return clips;
	}

	/**
	 * @see PainterLayout#getAugmentation(int)
	 */
	@Override
	public LayoutAugmentation getAugmentation(int count) {
		return new RowAugmentation(count);
	}

}
