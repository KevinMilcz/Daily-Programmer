import std.stdio, std.algorithm, std.range, std.functional, std.container;
 
 
 /*Challenge:
  *	https://www.reddit.com/r/dailyprogrammer/comments/3nkanm/20151005_challenge_235_easy_ruthaaron_pairs/
 */
 
 class prime_sieve
 {
 	private ulong[] primes;
 	private size_t cur_limit;
 	
 	this(ulong initial_limit)
 	{
 		cur_limit = 1;
 		primes = new ulong[cur_limit];
 		primes[0] = 2;
 		extend_primes(initial_limit);
 	}
 	
 	/* Sieve of Eratosthenes*/
 	private void extend_primes(in ulong limit)
 	{
 		cur_limit = limit;
 		bool[] composite = new bool[cur_limit];
 	 		
 		foreach (n; 2 .. cast(ulong)(limit ^^ 0.5) + 1)
        	if (!composite[n])
            	for (ulong k = n * n; k < limit; k += n)
                	composite[k] = true;
    	
    	primes = iota(2, limit).filter!(i => !composite[i]).array;
 	}
 	 	 
 	auto sieve(in ulong limit) 
 	{
 		if(cur_limit < limit)
 		{
 			auto new_limit = max(limit, cur_limit * 2);
 			extend_primes(new_limit);
 		}
 		
 		/* Find the index of the largest prime that is <= limit 
 		 * We could do a binary search, but since the density of primes scales
 		 * with about ln(limit), it's probably not worth it */
 		size_t end_ix = primes.length; 		
 		for(auto n = 1; n < primes.length; ++n)
 		{
			if(primes[n] > limit)
			{
				end_ix = n;
				break;
			}
 		}
 			 			 			
 		return primes[0 .. end_ix];
 	}
 }
 
Array!ulong trial_division(ulong n, prime_sieve the_sieve)
{
	if( n < 2)
		return Array!ulong();

	auto prime_factors = Array!ulong();
	
	foreach (immutable ulong prime; the_sieve.sieve(cast(ulong)(n ^^ 0.5) +1 ))
	{
		if(prime * prime > n)
			break;
		while(n % prime == 0)
		{
			prime_factors.insert(prime);
			n /= prime;
		}		
	}
	if(n > 1)
		prime_factors.insert(n);
		
	return prime_factors;
}

bool check_RA(T)(T a_primes, T b_primes)
{
	auto sum_a = 0;
	auto sum_b = 0;
	foreach(n; a_primes) { sum_a += n;}
	foreach(n; b_primes) { sum_b += n;}
	
	return sum_a == sum_b;
}

bool is_ruth_aaron(in ulong a, in ulong b, prime_sieve the_sieve)
{		
	auto a_primes = trial_division(a, the_sieve)[].sort().uniq;	
	auto b_primes = trial_division(b, the_sieve)[].sort().uniq;
	
	return check_RA(a_primes[], b_primes[]);
}

/* Calculate all RA's from 0 to limit. Optimize by not calculating
 * prime factorization for each number twice */
void all_ruth_aarons(ulong limit)
{
	auto the_sieve = new prime_sieve(limit);
	auto prev_primes = trial_division(limit, the_sieve)[].sort().uniq;
		
	for(ulong n = limit -1; n > 0 ; --n)
	{
		auto cur_primes = trial_division(n, the_sieve)[].sort().uniq;
		
		if(check_RA(cur_primes, prev_primes))
			writeln("Found a Ruth Aaron at ", n, ", ", n+1);
		prev_primes = cur_primes;
	}
}

void main() 
{
	auto the_sieve = new prime_sieve(100);
	writeln(is_ruth_aaron(714, 715, the_sieve));
	writeln(is_ruth_aaron(77,78, the_sieve));
	writeln(is_ruth_aaron(20,21, the_sieve));
	
	all_ruth_aarons(20000000);
}