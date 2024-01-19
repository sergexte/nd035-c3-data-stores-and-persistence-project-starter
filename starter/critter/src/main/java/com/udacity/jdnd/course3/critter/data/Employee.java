package com.udacity.jdnd.course3.critter.data;

import com.udacity.jdnd.course3.critter.user.employee.EmployeeSkill;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Nationalized;

import java.time.DayOfWeek;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Employee {

  @Id @GeneratedValue private long id;

  @Nationalized private String name;

  @ElementCollection(targetClass = EmployeeSkill.class)
  @Enumerated(EnumType.STRING)
  private Set<EmployeeSkill> skills;

  @ElementCollection(targetClass = DayOfWeek.class)
  @Enumerated(EnumType.STRING)
  private Set<DayOfWeek> availability;

  @ManyToMany(mappedBy = "employees")
  private Set<Schedule> schedules;
}
