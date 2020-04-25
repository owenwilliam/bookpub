package org.owen.bookpub.metrics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.geo.Metric;
import org.springframework.data.repository.CrudRepository;

/*public class DbCountMetrics implements PublicMetrics
{
	private Collection<CrudRepository> repositories;

	public DbCountMetrics(Collection<CrudRepository> repositories)
	{
		this.repositories = repositories;
	}

	@Override
	public Collection<Metric<?>> metrics()
	{
		List<Metric<?>> metrics = new LinkedList<>();
		for (CrudRepository repository : repositories)
		{
			String name = DbCountRunner
					.getRepositoryName(repository.getClass());
			String metricName = "counter.datasource." + name;
			metrics.add(new Metric(metricName, repository.count()));
		}
		return metrics;
	}
}*/