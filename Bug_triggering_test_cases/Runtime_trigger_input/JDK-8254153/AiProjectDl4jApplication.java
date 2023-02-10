
import org.apache.commons.math3.util.IterationListener;
import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.split.FileSplit;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.transferlearning.FineTuneConfiguration;
import org.deeplearning4j.nn.transferlearning.TransferLearning;
import org.deeplearning4j.nn.transferlearning.TransferLearningHelper;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.TrainingListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.zoo.PretrainedType;
import org.deeplearning4j.zoo.ZooModel;
import org.deeplearning4j.zoo.model.VGG16;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Slf4j
@SpringBootApplication
public class AiProjectDl4jApplication {

	static DataSetIterator trainIter;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AiProjectDl4jApplication.class, args);

		int seed = 12345;
		int numClasses = 2;


		ZooModel zooModel = VGG16.builder().build();
		ComputationGraph pretrainedNet = (ComputationGraph) zooModel.initPretrained(PretrainedType.IMAGENET);

		FineTuneConfiguration fineTuneConf = new FineTuneConfiguration.Builder()
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
				.updater(new Nesterovs(5e-5))
				.seed(seed)
				.build();


//		log.info(pretrainedNet.summary());

		ComputationGraph vgg16Transfer = new TransferLearning.GraphBuilder(pretrainedNet)
				.fineTuneConfiguration(fineTuneConf)
				.setFeatureExtractor("fc2")
				.removeVertexKeepConnections("predictions")
				.addLayer("predictions",
						new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
								.nIn(4096).nOut(numClasses)
								.weightInit(WeightInit.XAVIER)
								.activation(Activation.SOFTMAX).build(), "fc2")
				.build();

		TransferLearningHelper transferLearningHelper =
				new TransferLearningHelper(vgg16Transfer, "fc2");
//		vgg16Transfer.init();

//		log.info(vgg16Transfer.summary());

		getImages();
		DataSetPreProcessor preProcessor = new VGG16ImagePreProcessor() ;
		trainIter.setPreProcessor(preProcessor);

		vgg16Transfer.setListeners(new ScoreIterationListener(5));
		log.info("Training starting...");

		for (int i = 0; i < 5; i++) {
			while (trainIter.hasNext()) {
				DataSet currentFeaturized = trainIter.next();
				vgg16Transfer.fit(currentFeaturized);
			}
		}
		log.info("If no results shown -> Summary: Failed");



	}


	static void getImages() throws IOException {
		Random rng = new Random();
		File parentDir = new File("Path to Training Data"); 


		
		ParentPathLabelGenerator labelMaker = new ParentPathLabelGenerator();

		ImageRecordReader recordReader = new ImageRecordReader(224,224,3,labelMaker);
		recordReader.initialize(new FileSplit(new File(String.valueOf(parentDir))));

		trainIter = new RecordReaderDataSetIterator(recordReader,150,1,2);

	}
}
}
