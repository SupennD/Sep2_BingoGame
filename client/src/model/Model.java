package model;

import utility.observer.subject.LocalSubject;

/**
 * A facade interface to be implemented by a class to expose critical methods for interacting with the application. It
 * also acts as a subject in the observer pattern as part of the MVVM application.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public interface Model extends LocalSubject<Object, Object>
{
  // TODO: implement
}