package me.notkronos.meowhack.util.shader;

public abstract class GlObject
{

	protected int id = -1;

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

}
