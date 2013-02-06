// OtherDrops - a Bukkit plugin
// Copyright (C) 2011 Robert Sargant, Zarius Tularial, Celtic Minstrel
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	 See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.	 If not, see <http://www.gnu.org/licenses/>.

package com.gmail.zariust.otherdrops.drop;

import static java.lang.Math.round;

import java.util.Random;

import com.gmail.zariust.otherdrops.Dependencies;
import com.gmail.zariust.otherdrops.Log;
import com.gmail.zariust.otherdrops.OtherDrops;
import com.gmail.zariust.otherdrops.event.OccurredEvent;
import com.gmail.zariust.otherdrops.options.IntRange;
import com.gmail.zariust.otherdrops.subject.Target;

import org.bukkit.Location;

public class RealMoneyDrop extends MoneyDrop {
	public RealMoneyDrop(IntRange money) {
		this(money, 100.0);
	}

	public RealMoneyDrop(IntRange money, boolean shouldSteal) {
		this(money, 100.0, shouldSteal);
	}

	public RealMoneyDrop(IntRange money, double chance) {
		this(money, chance, false);
	}

	public RealMoneyDrop(IntRange money, double percent, boolean shouldSteal) { // Rome
		super(money.toDoubleRange(), percent, shouldSteal);
	}
	
	@Override
	protected int calculateQuantity(double amount, Random rng) {
		total = loot.getRandomIn(rng);
		total = round(total);
		return (int)amount;
	}
	
	@Override
	protected int performDrop(Target source, Location where, DropFlags flags, OccurredEvent occurrence) {
		occurrence.setOverrideDefault(this.overrideDefault);
		
		if(!Dependencies.hasMoneyDrop())
			Log.logWarning("Real money drop has been configured but MoneyDrop is not installed.");
		super.performDrop(source, where, flags, occurrence);
		
		return 1;
	}
	
	@Override
	protected void dropMoney(Target source, Location where, DropFlags flags, double amount) {
		if(!Dependencies.hasMoneyDrop()) {
			super.dropMoney(source, where, flags, amount);
			return;
		}
		if(flags.spread) {
			int dropAmount = (int)amount, digit = 10;
			while(dropAmount > 0) {
				int inThis = dropAmount % digit;
				dropAmount -= inThis;
				digit *= 10;
				if(inThis > 0) Dependencies.getMoneyDrop().dropMoney(where, inThis);
			}
		} else {
			Dependencies.getMoneyDrop().dropMoney(where, (int)amount);
		}
	}
}
