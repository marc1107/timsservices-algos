package aufgabe4;

import java.util.Objects;

public class TelKnoten
{
	int x;
	int y;

	public TelKnoten(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString()
	{
		return String.format("(%d/%d)", this.x, this.y);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof TelKnoten))
			return false;

		TelKnoten knoten = (TelKnoten) obj;
		if (knoten.x == this.x && knoten.y == this.y)
			return true;

		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.x, this.y);
	}
}
