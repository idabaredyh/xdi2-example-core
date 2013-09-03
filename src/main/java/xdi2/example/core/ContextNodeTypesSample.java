package xdi2.example.core;

import java.io.StringReader;
import java.util.Iterator;

import xdi2.core.ContextNode;
import xdi2.core.Graph;
import xdi2.core.features.nodetypes.XdiAbstractContext;
import xdi2.core.features.nodetypes.XdiAttributeClass;
import xdi2.core.features.nodetypes.XdiAttributeInstanceUnordered;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.io.MimeType;
import xdi2.core.io.XDIReaderRegistry;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3SubSegment;

public class ContextNodeTypesSample {

	public static void main(String[] args) throws Exception {

		// create and print a graph with a collection

		Graph graph = MemoryGraphFactory.getInstance().openGraph();
		ContextNode contextNode = graph.getRootContextNode().setContextNode(XDI3SubSegment.create("=markus"));

		XdiAttributeClass telAttributeClass = XdiAbstractContext.fromContextNode(contextNode).getXdiAttributeClass(XDI3SubSegment.create("+tel"), true);
		telAttributeClass.setXdiInstanceUnordered(null).getXdiValue(true).getContextNode().setLiteral("+1.206.555.1111");
		telAttributeClass.setXdiInstanceUnordered(null).getXdiValue(true).getContextNode().setLiteral("+1.206.555.2222");

		System.out.println(graph.toString(new MimeType("application/xdi+json;pretty=1")));

		// write and re-read the graph, then find and print the members of the attribute collection

		Graph graph2 = MemoryGraphFactory.getInstance().openGraph();
		XDIReaderRegistry.getAuto().read(graph2, new StringReader(graph.toString()));
		ContextNode contextNode2 = graph.getDeepContextNode(XDI3Segment.create("=markus"));

		XdiAttributeClass telCollection2 = XdiAbstractContext.fromContextNode(contextNode2).getXdiAttributeClass(XDI3SubSegment.create("+tel"), false);

		for (Iterator<XdiAttributeInstanceUnordered> i = telCollection2.getXdiInstancesUnordered(); i.hasNext(); ) {

			System.out.println(i.next().getXdiValue(false).getContextNode().getLiteral().getLiteralData());
		}
	}
}
