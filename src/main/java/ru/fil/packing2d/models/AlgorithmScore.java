package ru.fil.packing2d.models;

import java.util.Objects;

/**
 * Структура, хранящая в себе имя алгоритма и количество очков (высоту) как результат упаковки.
 */
public class AlgorithmScore {

    private String algorithmName;

    private int points;

    public AlgorithmScore(String algorithmName) {
        this.algorithmName = algorithmName;
        this.points = 0;
    }

    public AlgorithmScore(String algorithmName, int points) {
        this.algorithmName = algorithmName;
        this.points = points;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public AlgorithmScore copy() {
        return new AlgorithmScore(this.algorithmName, this.points);
    }

    public void addPoints(int points) {
        this.points += points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlgorithmScore score = (AlgorithmScore) o;
        return points == score.points && Objects.equals(algorithmName, score.algorithmName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithmName, points);
    }

    @Override
    public String toString() {
        return "AlgorithmScore: " +
                "algorithmName='" + algorithmName + '\'' +
                ", points=" + points + '\n';
    }
}
