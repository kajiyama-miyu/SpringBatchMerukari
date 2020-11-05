package com.example;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.Domain.Original;

@Configuration
@EnableBatchProcessing
public class SpringBatch {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired 
	public DataSource dataSource;
	
	@Bean
	public FlatFileItemReader<Original> reader(){
		
		FlatFileItemReader<Original> reader = new FlatFileItemReader<>();
		//CSVファイルなどの、フラットファイルの読み込みを行うクラス。
		reader.setResource(new ClassPathResource("merukari.csv"));
		//Resouseオブジェクトをインプットし、区切り文字やオブジェクトへのマッピングルールをカスタマイズできる。
		reader.setLineMapper(new DefaultLineMapper<Original>() {{
			//LineTokenizerとFieldSetMapperを用いてレコードを変換対象クラスへ変更する
			setLineTokenizer(new DelimitedLineTokenizer() {{
				//区切り文字を指定してレコードを分割するクラス
				setNames(new String[] {"id", "name", "conditionId", "categoryName", "brand", "price", "shipping", "description"});
				//1レコードの各項目に名前を付与する。FieldSetMapperで使われるFieldSetで設定した名前を用いて各項目を取り出すことができる。
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Original>() {{
				//文字列や数字などのつくべるな変換処理が不要な場合はこれを使用。targetTypeに変換クラスを指定する。
				//これによってDkelimitedLineTokenizerで指定した各項目の名前と一致するフィールドに値を自動的に設定したインスタンスを生成する。
				setTargetType(Original.class);
			}});
		}});
		
		return reader;
	}
	
	@Bean
	public MerukariItemProcessor processor() {
		return new MerukariItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Original> writer() {
		JdbcBatchItemWriter<Original> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.setSql("INSERT INTO original (id, name, condition_id, category_name, brand, price, shipping, description) VALUES (:id, :name, :conditionId, :categoryName, :brand, :price, :shipping, :description)");
		writer.setDataSource(dataSource);
		return writer;
	}
	
	@Bean
	public JobExecutionListener listener() {
		return new JobStartEndListener(new JdbcTemplate(dataSource));
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Original, Original>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	@Bean
	public Job testJob() {
		return jobBuilderFactory.get("testJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(step1())
				.end()
				.build();
	}

}
