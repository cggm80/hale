package eu.esdihumboldt.cst.corefunctions;

import static org.junit.Assert.assertTrue;

import org.geotools.data.DataUtilities;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.junit.Test;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.FeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import eu.esdihumboldt.commons.goml.align.Cell;
import eu.esdihumboldt.commons.goml.oml.ext.Transformation;
import eu.esdihumboldt.commons.goml.omwg.Property;
import eu.esdihumboldt.commons.goml.rdf.About;
import eu.esdihumboldt.commons.goml.rdf.Resource;
import eu.esdihumboldt.cst.transformer.service.rename.FeatureBuilder;


public class CentroidTest {
	
	private final String sourceLocalname = "FT1"; //$NON-NLS-1$
	private final String sourceLocalnamePropertyAGeom = "PropertyAGeom"; //$NON-NLS-1$
	private final String sourceNamespace = "http://esdi-humboldt.eu"; //$NON-NLS-1$
	
	private final String targetLocalname = "FT2"; //$NON-NLS-1$
	private final String targetLocalnamePropertyBGeom = "PropertyBGeom"; //$NON-NLS-1$
	private final String targetNamespace = "http://xsdi.org"; //$NON-NLS-1$
	
	@Test
	public void testConfigure() {
		CentroidFunction cf = new CentroidFunction();
		cf.configure(cf.getParameters());
	}
	
	
	@Test
	public void testTransformFeatureFeature() {
		
		// set up cell to use for testing
		Cell cell = new Cell();

		Transformation t = new Transformation();
		t.setService(new Resource(CentroidFunction.class.toString()));
		
		Property p1 = new Property(new About(this.sourceNamespace, this.sourceLocalname, this.sourceLocalnamePropertyAGeom));
		cell.setEntity1(p1);
		cell.setEntity2(new Property(new About(this.targetNamespace, this.targetLocalname, this.targetLocalnamePropertyBGeom)));
		p1.setTransformation(t);
		// build source and target Features
		
		
		
		SimpleFeatureType sourcetype = this.getFeatureType(
				this.sourceNamespace, 
				this.sourceLocalname, 
				this.sourceLocalnamePropertyAGeom,
				Polygon.class);
		SimpleFeatureType targettype = this.getFeatureType(
				this.targetNamespace, 
				this.targetLocalname, 
				this.targetLocalnamePropertyBGeom, 
				Point.class);
		GeometryFactory fac = new GeometryFactory();
				
		
		Feature source = SimpleFeatureBuilder.build(sourcetype, new Object[] {fac.createPolygon(fac.createLinearRing(new Coordinate[] {new Coordinate(0,0), new Coordinate (2,0), new Coordinate (2,2), new Coordinate(0,2), new Coordinate(0,0)} ),null) }, "1"); //$NON-NLS-1$
		Feature target = FeatureBuilder.buildFeature(targettype, source, true);
		
		
		// perform actual test
		CentroidFunction center = new CentroidFunction();
		center.configure(cell);

		Feature neu = center.transform(source, target);
		
		Polygon pOld = (Polygon)source.getDefaultGeometryProperty().getValue();
		Point c = (Point)neu.getProperty(
				this.targetLocalnamePropertyBGeom).getValue();

		assertTrue(c.equals(fac.createPoint(new Coordinate(1,1))));

	}

	private SimpleFeatureType getFeatureType(
			String featureTypeNamespace, 
			String featureTypeName, 
			String featuretypeGeometryPropertyName,
			Class <? extends Geometry> geom) {
		
		SimpleFeatureType ft = null;
		try {
			SimpleFeatureTypeBuilder ftbuilder = new SimpleFeatureTypeBuilder();
			ftbuilder.setName(featureTypeName);
			ftbuilder.setNamespaceURI(featureTypeNamespace);
			ftbuilder.add(featuretypeGeometryPropertyName, geom);
			ftbuilder.setDefaultGeometry(featuretypeGeometryPropertyName);
			ft = ftbuilder.buildFeatureType();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return ft;
	}

}