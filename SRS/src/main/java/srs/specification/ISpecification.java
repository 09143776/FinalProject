package srs.specification;

import srs.model.EnrollmentStatus;

public interface ISpecification<T> {
	public EnrollmentStatus  isSatified(T entity);
}
