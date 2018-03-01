package com.datawalk.hashcode.algorithm;

import java.util.Arrays;
import java.util.Collection;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.Graph;
import org.apache.flink.graph.Vertex;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GSASingleSourceSample {

	public static DataSet<Vertex> gsaSample(ExecutionEnvironment env) throws Exception {
		// create a new vertex with a Long ID and a String value
		Vertex<Long, String> v1 = new Vertex<Long, String>(1L, "foo1");
		Vertex<Long, String> v2 = new Vertex<Long, String>(2L, "foo2");
		Vertex<Long, String> v3 = new Vertex<Long, String>(3L, "foo3");
		Vertex<Long, String> v4 = new Vertex<Long, String>(4L, "foo4");

		Collection vertexCollection = Arrays.asList(v1, v2, v3, v4);

		Edge<Long, Double> e1 = new Edge<Long, Double>(1L, 2L, 0.1);
		Edge<Long, Double> e2 = new Edge<Long, Double>(2L, 3L, 0.4);
		Edge<Long, Double> e3 = new Edge<Long, Double>(3L, 4L, 2.5);
		Edge<Long, Double> e4 = new Edge<Long, Double>(1L, 4L, 1.5);
		Edge<Long, Double> e5 = new Edge<Long, Double>(1L, 3L, 0.2);

		Collection edgeColleciton = Arrays.asList(e1, e2, e3, e4, e5);

		// DataSet<Vertex<String, Long>> vertices = env.fromElements(v1,v2,v3,v4)
		//
		// DataSet<Edge<String, Double>> edges = ...

		Graph<String, Long, Double> graph = Graph.fromCollection(vertexCollection, edgeColleciton, env);
		log.info("Num of Edges --> " + graph.numberOfEdges());
		log.info("Num of Vertex --> " + graph.numberOfVertices());
		log.info("Out Degrees --> " + graph.outDegrees());
		log.info("In Degrees --> " + graph.outDegrees());

		GSASingleSourceShortestPaths algorithm = new GSASingleSourceShortestPaths(1L, 10);
		DataSet<Vertex> result = algorithm.run(graph);
		return result;
	}

}
